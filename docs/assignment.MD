About the exercise:

The exercise goal is to write a functioning web backend in the language and stack of your choice to run our web-based stock market game.
The goal is to generate random stock values for a set of fictional stocks - we will use this as a backend for our game.


It should:

- Maintain a list of stock tickers. For now we'll start with 10 of them. We can name them anything, but to keep it simple let's say they are "stock-1", "stock-2"... all the way to "stock-10".
- All stocks start at a price of 1000 pounds.
- Prices are round pounds - we've decided we don't need to deal with pennies here ;)
- For our game's purposes, stocks continuously go up and down in price, in a random manner. Our scientists in the stock price analysis division are coming up with a very complicated function to simulate the actual stock market - but this formula isn't ready yet! In the meantime, what we know is:
  -- The function can calculate the next price for any given stock.
  -- The inputs of the function are the stock name and the current stock price at any given time (with time resolution down to a second)
  -- The output of the function is a random whole positive number, which will be the stock price at the next second.
  -- The function is actually computationally very expensive to run - our scientists think it will take any modern CPU core up to 1 second to run it. Luckily our servers have many CPU cores so we should be able to find a way around that!


What we want to do:

- Have a backend that spins up and initialises those 10 tickers at 1000 pounds each.
- Publish an HTTP endpoint that allows us to query the current value of a given stock, e.g. "/stocks/stock-1" would return 1000 when the server just started, and probably a different value one second later.
- Publish a second HTTP endpoint that allows us to query the names of the three more popular stocks. Stock popularity is set by the amount of times each ticker value has been queried using the previous endpoint, since our server started. In case of a tie you can arbitrarily return any of the tied stock names. We can publish this under a "/popular-stocks" endpoint.
- Lastly, we'd like to have a third endpoint that returns the sum of the current value of all stocks. This is a last minute requirement by our scientists; they'll need it in order to debug their formula. Their only request is that this endpoint always returns a stable sum of values for all stocks in the same second; i.e. the sum of stock values should take into account the sum of values of all stocks for the latest second when they are all available. In other words, it shouldn't mix stock values from the previous calculation iteration and the current one. We can publish this as "/sum-stocks".

While we wait for our scientists to produce the final formula, we can use a mock function and simulate it takes up to 1 second to return. The return value of the function can be any random whole positive number.


What we won't be doing:

- Authentication or authorization of any sorts
- Properly formatted responses, DTOs, JSON, etc.
- Observability, build pipelines, dockerization, or any other production level concerns we would look into in a real world scenario.
- Connecting to any real stock market APIs; this is a closed simulation.
- Persistence of any sorts. It's ok to lose all the data on server restarts.
- Horizontal scalability for the web server. We're happy with one node serving the requests. If your solution requires other nodes to do other work that's ok.
- Actual working cross service communication. If your solution requires two or more backend services talking to each other, that's ok - just mock it with a dummy function or interface. We don't need to see it working - we just need to understand what you're using and how and why you're using it.


What we're looking for:

- Usage of good coding and architecture patterns that allow us to keep this application easy to expand, maintain and scale.
- Some extra skill show-offs will be appreciated, like automated testing for core parts of the application.
- Note the price calculation function introduces a deliberate delay to simulate computational expense. You can assume this is a realistic constraint.

We've coded a base Springboot skeleton app that contains a mock stock generation function as well as mocks for the three endpoints. You can use it as a starting point for your solution, or just as inspiration. Find the code here: https://github.com/amelio-tech/interviews-stocks


How to submit:

Please share your code in a zip file directly via email, or in a private Github / BitBucket repository shared with me.