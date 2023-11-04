# Jokes API
This spring boot application has two endpoints, a simple one /api/random-joke/ that can be called with a
GET-request to return a safe, non-explicit, non-sexist joke without any user input parameters.

There is another endpoint, /api/specify-joke/ where the user can provide path variables and query parameters
to specify the collection of jokes that can be selected from. It also returns a safe, non-explicit,
non-sexist joke.