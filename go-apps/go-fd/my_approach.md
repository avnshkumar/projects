My approach:

APIs

Functionalities
- API to get all filter values
- API to get all sub-filter(secondary level filters with multi-select) values
- API to fetch paginated forecasting data with above filter
- API to fetch any other data required for the dashboard table ( primary screen)

- API to update one item X store
- API to update multiple items X stores ( all the options below can be clubbed as part of filter config)
    - What if there are too many items to update?
    - API to apply updates via filter
    - select/deselect items after applying filters


Able to create recurrence - Filter Config + Changes across items ( change factor can be across one item or entire filter)


Trend/ Anomalies Detection

- Under prediction/ Over prediction
- Flag abnormal forecast behavior
    - Flood anomaly annotation
    - Festival anomaly annotation




Variables
- Need discussion to understand
- Is it part of dashboard or forecasting


Insights
- DoD ( cron job and store in db as it only relies on past data)
    - CPC/DTS level
    - All/ L2/ Ptype levels 
    - Base vs Final forecast ( forecast change comparison)
    - Sales vs availability vs final forecast ( forecast accuracy and gaps)
- WoW data


Business Insights
- SKU level breakages
    - Backend
    - City
    - Store
Common Breakage Categories:
- Low Availability SKUs
- High Dump SKUs
- High Leftover SKUs

Diagnostic Tags:
- Underforecasting
- Overforecasting
- NR/Picking Gap / Dump / Quality Issues
- Fills Issues


Growth De-growth with reasoning:
Input Reason Tags for changes:
- Promotion
- Seasonality
- Pricing Change
- External Factors (e.g. Weather, Disruption)


Deep Dive Overview:
- How is Mango performing Pan India?
- What are the key issues in Mango forecasting?
- Which Mango SKUs are growing fastest?
- Are Mango SKUs profitable?



PO items:
- Fetch Forecasting data
- Filter and Grouping APIs
- Save forecasting overrides done via users ( chain of changes applied)
- Growth De-growth with reasoning


P1 items:
- Diagnostic Tags
- Crown for recurring changes
- Trend/ Anomalies Detection
- Forecasting Overview 
- Common Breakage Categories:
- Business Insights


P2 items:
- Deep dive overview


Open Question:
How do we apply changes?
Will there will be single version where all changes will be applied, how do manage multiple people editing the same dataset?
