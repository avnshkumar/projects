package main

import (
	"html/template"
	"log"
	"net/http"
)

func main() {
	// Static files handler
	fs := http.FileServer(http.Dir("static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))

	// Home route
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		tmpl := template.Must(template.ParseFiles("templates/index.html"))
		tmpl.Execute(w, nil)
	})

	log.Println("Server started on http://localhost:8080")
	http.ListenAndServe(":8080", nil)
}