
# Forecasting Dashboard Backend Design - Initial Draft

## Project Overview
Building a Go + Gin-Gonic backend for a forecasting dashboard that replaces manual Excel work. The tool handles large CSV inputs (10MB) and provides APIs for forecast management with key-based authentication.

## Core Requirements
- Update quantity of items with reasoning
- Bulk updates with multi-column grouping
- 5-6 major columns with customizable multi-select filters
- Internal tool with API key authentication
- CSV processing (10MB files)

---

## 1. Database Schema Design

### Core Tables

```sql
-- Core forecast data table
CREATE TABLE forecasts (
    id BIGSERIAL PRIMARY KEY,
    item_id VARCHAR(50) NOT NULL,
    store_id VARCHAR(50) NOT NULL,
    backend VARCHAR(50) NOT NULL,
    l1_category VARCHAR(100),
    l2_category VARCHAR(100),
    cpc VARCHAR(50),
    store_cluster VARCHAR(50),
    base_forecast DECIMAL(10,2),
    final_forecast DECIMAL(10,2),
    forecast_date DATE NOT NULL,
    availability_bucket VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

-- Track all forecast changes with reasoning
CREATE TABLE forecast_adjustments (
    id BIGSERIAL PRIMARY KEY,
    forecast_id BIGINT REFERENCES forecasts(id),
    old_quantity DECIMAL(10,2),
    new_quantity DECIMAL(10,2),
    adjustment_type VARCHAR(20) CHECK (adjustment_type IN ('growth', 'degrowth', 'correction')),
    reason_category VARCHAR(50), -- 'promotion', 'seasonality', 'pricing', 'external'
    reason_details TEXT,
    adjustment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    adjusted_by VARCHAR(100),
    is_recurring BOOLEAN DEFAULT false,
    cron_expression VARCHAR(100),
    batch_id UUID -- For bulk operations tracking
);

-- Store custom filter configurations
CREATE TABLE saved_filters (
    id BIGSERIAL PRIMARY KEY,
    filter_name VARCHAR(100),
    filter_config JSONB, -- Store complex filter combinations
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Database Indexes

```sql
-- Indexes for optimal performance
CREATE INDEX idx_forecasts_composite ON forecasts(item_id, store_id, forecast_date);
CREATE INDEX idx_forecasts_categories ON forecasts(l1_category, l2_category);
CREATE INDEX idx_forecasts_location ON forecasts(backend, store_cluster);
CREATE INDEX idx_forecasts_date ON forecasts(forecast_date);
CREATE INDEX idx_adjustments_forecast ON forecast_adjustments(forecast_id);
CREATE INDEX idx_adjustments_batch ON forecast_adjustments(batch_id);
```

---

## 2. Go Data Models

### Core Structs

```go
// Core forecast model
type Forecast struct {
    ID                uint`json:"id" gorm:"primaryKey"`
    ItemID            string    `json:"item_id" gorm:"not null" validate:"required"`
    StoreID           string    `json:"store_id" gorm:"not null" validate:"required"`
    Backend           string    `json:"backend" gorm:"not null" validate:"required"`
    L1Category        string    `json:"l1_category"`
    L2Category        string    `json:"l2_category"`
    CPC               string    `json:"cpc"`
    StoreCluster      string    `json:"store_cluster"`
    BaseForecast      float64   `json:"base_forecast"`
    FinalForecast     float64   `json:"final_forecast"`
    ForecastDate      time.Time `json:"forecast_date" gorm:"not null" validate:"required"`
    AvailabilityBucket string   `json:"availability_bucket"`
    CreatedAt         time.Time `json:"created_at"`
    UpdatedAt         time.Time `json:"updated_at"`
    CreatedBy         string    `json:"created_by"`
    UpdatedBy         string    `json:"updated_by"`
}

// Forecast adjustment tracking
type ForecastAdjustment struct {
    ID               uint      `json:"id" gorm:"primaryKey"`
    ForecastID       uint      `json:"forecast_id" validate:"required"`
    OldQuantity      float64   `json:"old_quantity"`
    NewQuantity      float64   `json:"new_quantity" validate:"required"`
    AdjustmentType   string    `json:"adjustment_type" validate:"required,oneof=growth degrowth correction"`
    ReasonCategory   string    `json:"reason_category" validate:"required"`
    ReasonDetails    string    `json:"reason_details"`
    AdjustmentDate   time.Time `json:"adjustment_date"`
    AdjustedBy       string    `json:"adjusted_by" validate:"required"`
    IsRecurring      bool      `json:"is_recurring"`
    CronExpression   string    `json:"cron_expression"`
    BatchID          string    `json:"batch_id"`
    Forecast         Forecast  `json:"forecast" gorm:"foreignKey:ForecastID"`
}

// Filter configuration
type FilterConfig struct {
    L1Categories        []string `json:"l1_categories,omitempty"`
    L2Categories        []string `json:"l2_categories,omitempty"`
    CPCs                []string `json:"cpcs,omitempty"`
    Backends            []string `json:"backends,omitempty"`
    StoreClusters       []string `json:"store_clusters,omitempty"`
    ItemIDs             []string `json:"item_ids,omitempty"`
    StoreIDs            []string `json:"store_ids,omitempty"`
    DateFrom            string   `json:"date_from,omitempty"`
    DateTo              string   `json:"date_to,omitempty"`
    AvailabilityBuckets []string `json:"availability_buckets,omitempty"`
}

// Bulk edit request
type BulkEditRequest struct {
    Filters          FilterConfig `json:"filters" validate:"required"`
    UpdateType       string       `json:"update_type" validate:"required,oneof=quantity adjustment"`
    NewQuantity      *float64     `json:"new_quantity,omitempty"`
    AdjustmentType   string       `json:"adjustment_type,omitempty" validate:"omitempty,oneof=growth degrowth correction"`
    ReasonCategory   string       `json:"reason_category" validate:"required"`
    ReasonDetails    string       `json:"reason_details"`
    IsRecurring      bool         `json:"is_recurring"`
    CronExpression   string       `json:"cron_expression,omitempty"`
    UpdatedBy        string       `json:"updated_by" validate:"required"`
}
```

---

## 3. API Authentication Middleware

### Key-based Authentication

```go
package middleware

import (
    "net/http"
    "strings"
    "github.com/gin-gonic/gin"
)

// APIKeyAuth middleware for key-based authentication
func APIKeyAuth() gin.HandlerFunc {
    return func(c *gin.Context) {
        apiKey := c.GetHeader("X-API-Key")
        
        if apiKey == "" {
            // Check for API key in query params as fallback
            apiKey = c.Query("api_key")
        }
        
        if apiKey == "" {
            c.JSON(http.StatusUnauthorized, gin.H{
                "error": "API key required",
                "code":  "MISSING_API_KEY",
            })
            c.Abort()
            return
        }
        
        // Validate API key (implement your validation logic)
        if !isValidAPIKey(apiKey) {
            c.JSON(http.StatusUnauthorized, gin.H{
                "error": "Invalid API key",
                "code":  "INVALID_API_KEY",
            })
            c.Abort()
            return
        }
        
        // Extract user info from API key and set in context
        userInfo := getUserInfoFromAPIKey(apiKey)
        c.Set("user_id", userInfo.UserID)
        c.Set("user_role", userInfo.Role)
        
        c.Next()
    }
}

// Rate limiting middleware
func RateLimit() gin.HandlerFunc {
    return func(c *gin.Context) {
        apiKey := c.GetHeader("X-API-Key")
        
        // Implement rate limiting logic based on API key
        if isRateLimited(apiKey) {
            c.JSON(http.StatusTooManyRequests, gin.H{
                "error": "Rate limit exceeded",
                "code":  "RATE_LIMIT_EXCEEDED",
            })
            c.Abort()
            return
        }
        
        c.Next()
    }
}

type UserInfo struct {
    UserID string
    Role   string
}

func isValidAPIKey(apiKey string) bool {
    // Implement your API key validation logic
    // This could check against a database, environment variables, etc.
    validKeys := getValidAPIKeys() // From config or database
    for _, validKey := range validKeys {
        if apiKey == validKey {
            return true
        }
    }
    return false
}

func getUserInfoFromAPIKey(apiKey string) UserInfo {
    // Extract user information from API key
    // This could be from a lookup table or encoded in the key
    return UserInfo{
        UserID: "user_" + apiKey[:8], // Simple example
        Role:   "forecaster",
    }
}
```

---

## 4. Multi-Level Filtering System

### Filter Service

```go
package service

import (
    "gorm.io/gorm"
    "strings"
)

type FilterService struct {
    db *gorm.DB
}

func NewFilterService(db *gorm.DB) *FilterService {
    return &FilterService{db: db}
}

// BuildQuery constructs dynamic query based on filter configuration
func (fs *FilterService) BuildQuery(filters FilterConfig) *gorm.DB {
    query := fs.db.Model(&Forecast{})
    
    // Apply multi-select filters
    if len(filters.L1Categories) > 0 {
        query = query.Where("l1_category IN ?", filters.L1Categories)
    }
    if len(filters.L2Categories) > 0 {
        query = query.Where("l2_category IN ?", filters.L2Categories)
    }
    
    if len(filters.CPCs) > 0 {
        query = query.Where("cpc IN ?", filters.CPCs)
    }
    
    if len(filters.Backends) > 0 {
        query = query.Where("backend IN ?", filters.Backends)
    }
    
    if len(filters.StoreClusters) > 0 {
        query = query.Where("store_cluster IN ?", filters.StoreClusters)
    }
    
    if len(filters.ItemIDs) > 0 {
        query = query.Where("item_id IN ?", filters.ItemIDs)
    }
    
    if len(filters.StoreIDs) > 0 {
        query = query.Where("store_id IN ?", filters.StoreIDs)
    }
    
    if len(filters.AvailabilityBuckets) > 0 {
        query = query.Where("availability_bucket IN ?", filters.AvailabilityBuckets)
    }
    
    // Date range filters
    if filters.DateFrom != "" {
        query = query.Where("forecast_date >= ?", filters.DateFrom)
    }
    
    if filters.DateTo != "" {
        query = query.Where("forecast_date <= ?", filters.DateTo)
    }
    
    return query
}

// GetFilterOptions returns available values for each filter dimension
func (fs *FilterService) GetFilterOptions() (map[string][]string, error) {
    options := make(map[string][]string)
    
    // Get unique L1 categories
    var l1Categories []string
    fs.db.Model(&Forecast{}).Distinct("l1_category").Pluck("l1_category", &l1Categories)
    options["l1_categories"] = l1Categories
    
    // Get unique L2 categories
    var l2Categories []string
    fs.db.Model(&Forecast{}).Distinct("l2_category").Pluck("l2_category", &l2Categories)
    options["l2_categories"] = l2Categories
    
    // Get unique CPCs
    var cpcs []string
    fs.db.Model(&Forecast{}).Distinct("cpc").Pluck("cpc", &cpcs)
    options["cpcs"] = cpcs
    
    // Get unique backends
    var backends []string
    fs.db.Model(&Forecast{}).Distinct("backend").Pluck("backend", &backends)
    options["backends"] = backends
    
    // Get unique store clusters
    var storeClusters []string
    fs.db.Model(&Forecast{}).Distinct("store_cluster").Pluck("store_cluster", &storeClusters)
    options["store_clusters"] = storeClusters
    
    // Get unique availability buckets
    var availabilityBuckets []string
    fs.db.Model(&Forecast{}).Distinct("availability_bucket").Pluck("availability_bucket", &availabilityBuckets)
    options["availability_buckets"] = availabilityBuckets
    
    return options, nil
}

// GroupBy functionality for aggregated views
func (fs *FilterService) GetGroupedResults(filters FilterConfig, groupBy []string) ([]GroupedResult, error) {
    query := fs.BuildQuery(filters)
    
    var results []GroupedResult
    
    // Build dynamic GROUP BY clause
    groupByClause := strings.Join(groupBy, ", ")
    selectClause := groupByClause + ", COUNT(*) as count, SUM(final_forecast) as total_forecast, AVG(final_forecast) as avg_forecast"
    
    err := query.Select(selectClause).Group(groupByClause).Scan(&results).Error
    return results, err
}

type GroupedResult struct {
    L1Category      string  `json:"l1_category,omitempty"`
    L2Category      string  `json:"l2_category,omitempty"`
    CPC             string  `json:"cpc,omitempty"`
    Backend         string  `json:"backend,omitempty"`
    StoreCluster    string  `json:"store_cluster,omitempty"`
    Count           int64   `json:"count"`
    TotalForecast   float64 `json:"total_forecast"`
    AvgForecast     float64 `json:"avg_forecast"`
}
```

---

## 5. Bulk Editing Operations

### Bulk Edit Service

```go
package service

import (
    "github.com/google/uuid"
    "gorm.io/gorm"
    "time"
    "fmt"
)

type BulkEditService struct {
    db *gorm.DB
    filterService *FilterService
}

func NewBulkEditService(db *gorm.DB, filterService *FilterService) *BulkEditService {
    return &BulkEditService{
        db: db,
        filterService: filterService,
    }
}

// PerformBulkEdit executes bulk update operations with transaction management
func (bes *BulkEditService) PerformBulkEdit(request BulkEditRequest) (*BulkEditResult, error) {
    batchID := uuid.New().String()
    
    // Start transaction
    tx := bes.db.Begin()
    if tx.Error != nil {
        return nil, tx.Error
    }
    
    defer func() {
        if r := recover(); r != nil {
            tx.Rollback()
        }
    }()
    
    // Get records matching filters
    query := bes.filterService.BuildQuery(request.Filters)
    
    var forecasts []Forecast
    if err := query.Find(&forecasts).Error; err != nil {
        tx.Rollback()
        return nil, err
    }
    
    if len(forecasts) == 0 {
        tx.Rollback()
        return &BulkEditResult{
            Success: false,
            Message: "No records match the specified filters",
        }, nil
    }
    
    // Prepare adjustments
    var adjustments []ForecastAdjustment
    updatedRecords := 0
    
    for _, forecast := range forecasts {
        oldQuantity := forecast.FinalForecast
        newQuantity := oldQuantity
        
        // Apply update logic
        switch request.UpdateType {
        case "quantity":
            if request.NewQuantity != nil {
                newQuantity = *request.NewQuantity
            }
        case "adjustment":
            // Apply percentage adjustment
            if request.AdjustmentType == "growth" {
                newQuantity = oldQuantity * 1.1 // Example: 10% growth
            } else if request.AdjustmentType == "degrowth" {
                newQuantity = oldQuantity * 0.9 // Example: 10% degrowth
            }
        }
        
        // Update forecast record
        if err := tx.Model(&forecast).Updates(map[string]interface{}{
            "final_forecast": newQuantity,
            "updated_at":     time.Now(),"updated_by":     request.UpdatedBy,
        }).Error; err != nil {
            tx.Rollback()
            return nil, err
        }
        
        // Create adjustment record
        adjustment := ForecastAdjustment{
            ForecastID:forecast.ID,
            OldQuantity:oldQuantity,
            NewQuantity:    newQuantity,
            AdjustmentType: request.AdjustmentType,
            ReasonCategory: request.ReasonCategory,
            ReasonDetails:  request.ReasonDetails,
            AdjustedBy:     request.UpdatedBy,
            IsRecurring:    request.IsRecurring,
            CronExpression: request.CronExpression,
            BatchID:        batchID,
            AdjustmentDate: time.Now(),
        }
        
        adjustments = append(adjustments, adjustment)
        updatedRecords++
    }
    
    // Bulk insert adjustments
    if err := tx.Create(&adjustments).Error; err != nil {
        tx.Rollback()
        return nil, err
    }
    
    // Commit transaction
    if err := tx.Commit().Error; err != nil {
        return nil, err
    }
    
    return &BulkEditResult{
        Success:        true,
        RecordsUpdated: updatedRecords,
        BatchID:        batchID,
        Message:        fmt.Sprintf("Successfully updated %d records", updatedRecords),
    }, nil
}

// PreviewBulkEdit shows what would be changed without actually updating
func (bes *BulkEditService) PreviewBulkEdit(request BulkEditRequest) (*BulkEditPreview, error) {
    query := bes.filterService.BuildQuery(request.Filters)
    
    var count int64
    if err := query.Count(&count).Error; err != nil {
        return nil, err
    }
    
    // Get sample records for preview
    var samples []Forecastif err := query.Limit(5).Find(&samples).Error; err != nil {
        return nil, err
    }
    
    return &BulkEditPreview{
        TotalRecords:   count,
        SampleRecords:  samples,
        UpdateType:     request.UpdateType,
        AdjustmentType: request.AdjustmentType,}, nil
}

type BulkEditResult struct {
    Success        bool   `json:"success"`
    RecordsUpdated int    `json:"records_updated"`
    BatchID        string `json:"batch_id"`
    Message        string `json:"message"`
}

type BulkEditPreview struct {
    TotalRecords   int64      `json:"total_records"`
    SampleRecords  []Forecast `json:"sample_records"`
    UpdateType     string     `json:"update_type"`
    AdjustmentType string     `json:"adjustment_type"`
}
```

---

## 6. API Endpoints Structure

### Proposed API Routes

```go
// Core forecast management
GET    /api/v1/forecasts                    // List forecasts with filters
POST   /api/v1/forecasts                    // Create new forecast
GET    /api/v1/forecasts/:id                // Get single forecast
PUT    /api/v1/forecasts/:id                // Update single forecast
DELETE /api/v1/forecasts/:id                // Delete forecast

// Bulk operations
POST   /api/v1/forecasts/bulk-edit          // Perform bulk edit
POST   /api/v1/forecasts/bulk-preview       // Preview bulk changes
GET    /api/v1/forecasts/bulk-history       // Get bulk operation history

// Filtering and grouping
GET    /api/v1/filters/options              // Get all filter options
POST   /api/v1/filters/apply                // Apply complex filters
GET    /api/v1/filters/saved                // Get saved filter configs
POST   /api/v1/filters/save                 // Save filter configuration
POST   /api/v1/forecasts/group              // Get grouped results

// Adjustments and audit
GET    /api/v1/adjustments                  // List all adjustments
GET    /api/v1/adjustments/batch/:batch_id  // Get adjustments by batch
GET    /api/v1/forecasts/:id/adjustments    // Get forecast adjustment history

// CSV operations (handled separately)
POST   /api/v1/upload/csv                   // Upload and process CSV
```

---

## 7. Next Steps

### Remaining Implementation Tasks:

1. **Database migrations and indexes** - Create migration files for schema setup
2. **API endpoints implementation** - Build controllers and routes
3. **Comprehensive validation** - Input validation for all operations
4. **Error handling standardization** - Consistent error responses
5. **Testing framework** - Unit and integration tests
6. **Performance optimization** - Query optimization and caching
7. **Documentation** - API documentation and usage examples

### Technology Stack:
- **Backend**: Go 1.21+ with Gin-Gonic framework
- **Database**: PostgreSQL with GORM ORM
- **Authentication**: API key-based with middleware
- **Validation**: Go validator package
- **CSV Processing**: External parser (as specified)
- **Deployment**: Internal environment with key-based access

This design provides a solid foundation for the forecasting dashboard backend, focusing on the core requirements of multi-level filtering, bulk editing, and secure API access while maintaining scalability and data integrity.