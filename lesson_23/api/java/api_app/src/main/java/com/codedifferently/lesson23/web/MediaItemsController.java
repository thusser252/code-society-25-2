package com.codedifferently.lesson23.web;

import com.codedifferently.lesson23.library.Librarian;
import com.codedifferently.lesson23.library.Library;
import com.codedifferently.lesson23.library.MediaItem;
import com.codedifferently.lesson23.library.search.SearchCriteria;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MediaItemsController {

  private final Library library;
  private final Librarian librarian;

  public MediaItemsController(Library library) throws IOException {
    this.library = library;
    this.librarian = library.getLibrarians().stream().findFirst().orElseThrow();
  }

  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> welcome() {
    Map<String, Object> response =
        Map.of(
            "message", "Welcome to the Library Management API!",
            "version", "1.0.0",
            "endpoints",
                Map.of(
                    "DELETE /items/{id}", "Delete a media item",
                    "POST /items", "Create a new media item",
                    "GET /items", "Get all media items",
                    "GET /items/{id}", "Get a specific media item"),
            "documentation",
                Map.of(
                    "openapi", "/v3/api-docs",
                    "swagger", "/swagger-ui.html"),
            "statistics",
                Map.of(
                    "totalItems", library.search(SearchCriteria.builder().build()).size(),
                    "totalLibrarians", library.getLibrarians().size()));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/items")
  public ResponseEntity<GetMediaItemsResponse> getItems() {
    Set<MediaItem> items = library.search(SearchCriteria.builder().build());
    List<MediaItemResponse> responseItems = items.stream().map(MediaItemResponse::from).toList();
    var response = GetMediaItemsResponse.builder().items(responseItems).build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/items/{id}")
  public ResponseEntity<MediaItemResponse> getItem(@PathVariable String id) {
    Set<MediaItem> items = library.search(SearchCriteria.builder().id(id).build());
    if (items.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    MediaItem item = items.iterator().next();
    MediaItemResponse response = MediaItemResponse.from(item);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/items")
  public ResponseEntity<CreateMediaItemResponse> createItem(
      @Valid @RequestBody CreateMediaItemRequest request) {
    MediaItem mediaItem = MediaItemRequest.asMediaItem(request.getItem());
    library.addMediaItem(mediaItem, librarian);
    MediaItemResponse responseItem = MediaItemResponse.from(mediaItem);
    var response = CreateMediaItemResponse.builder().item(responseItem).build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/swagger-ui.html")
  public ResponseEntity<String> getSwaggerUI() {
    String swaggerHtml = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Library Management API Documentation</title>
            <link rel="stylesheet" type="text/css" href="https://unpkg.com/swagger-ui-dist@3.52.5/swagger-ui.css" />
            <style>
                html { box-sizing: border-box; overflow: -moz-scrollbars-vertical; overflow-y: scroll; }
                *, *:before, *:after { box-sizing: inherit; }
                body { margin:0; background: #fafafa; }
            </style>
        </head>
        <body>
            <div id="swagger-ui"></div>
            <script src="https://unpkg.com/swagger-ui-dist@3.52.5/swagger-ui-bundle.js"></script>
            <script src="https://unpkg.com/swagger-ui-dist@3.52.5/swagger-ui-standalone-preset.js"></script>
            <script>
                window.onload = function() {
                    const ui = SwaggerUIBundle({
                        url: '/v3/api-docs',
                        dom_id: '#swagger-ui',
                        deepLinking: true,
                        presets: [
                            SwaggerUIBundle.presets.apis,
                            SwaggerUIStandalonePreset
                        ],
                        plugins: [
                            SwaggerUIBundle.plugins.DownloadUrl
                        ],
                        layout: "StandaloneLayout"
                    });
                };
            </script>
        </body>
        </html>
        """;
    return ResponseEntity.ok()
        .header("Content-Type", "text/html")
        .body(swaggerHtml);
  }

  @GetMapping("/v3/api-docs")
  public ResponseEntity<Map<String, Object>> getApiDocs() {
    Map<String, Object> openApiSpec = Map.of(
        "openapi", "3.0.1",
        "info", Map.of(
            "title", "Library Management API",
            "description", "A simple API for managing library media items",
            "version", "1.0.0"
        ),
        "servers", List.of(
            Map.of("url", "http://localhost:3001", "description", "Development server")
        ),
        "paths", Map.of(
            "/", Map.of(
                "get", Map.of(
                    "summary", "Get welcome message",
                    "responses", Map.of(
                        "200", Map.of("description", "Welcome message")
                    )
                )
            ),
            "/items", Map.of(
                "get", Map.of(
                    "summary", "Get all media items",
                    "responses", Map.of(
                        "200", Map.of("description", "List of media items")
                    )
                ),
                "post", Map.of(
                    "summary", "Create a new media item",
                    "responses", Map.of(
                        "200", Map.of("description", "Created media item")
                    )
                )
            ),
            "/items/{id}", Map.of(
                "get", Map.of(
                    "summary", "Get media item by ID",
                    "parameters", List.of(
                        Map.of("name", "id", "in", "path", "required", true, 
                               "schema", Map.of("type", "string"))
                    ),
                    "responses", Map.of(
                        "200", Map.of("description", "Media item"),
                        "404", Map.of("description", "Item not found")
                    )
                ),
                "delete", Map.of(
                    "summary", "Delete media item by ID",
                    "parameters", List.of(
                        Map.of("name", "id", "in", "path", "required", true, 
                               "schema", Map.of("type", "string"))
                    ),
                    "responses", Map.of(
                        "204", Map.of("description", "Item deleted"),
                        "404", Map.of("description", "Item not found")
                    )
                )
            )
        )
    );
    return ResponseEntity.ok(openApiSpec);
  }

  @DeleteMapping("/items/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable String id) {
    Set<MediaItem> items = library.search(SearchCriteria.builder().id(id).build());
    if (items.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    MediaItem item = items.iterator().next();
    library.removeMediaItem(item, librarian);
    return ResponseEntity.noContent().build();
  }
}
