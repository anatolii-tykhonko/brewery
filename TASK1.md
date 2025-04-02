Overview
Build a Spring Boot application that simulates the brewing of different beer types. The goal is to expose a REST API to initiate and monitor the brewing process, handling errors and batch statuses appropriately.

### Important:
No database should be used in the initial stage. All data must be stored in-memory. However, design your code with a repository layer interface so it can be swapped out for database access in the future.

- Provide a code that you are comfortable putting in production.
- Write tests for your code.

### Functional Requirements, create REST API for the following:

1. Start Brewing API
- Accepts a beer type and amount in litres.
- Only one batch of a given beer type can be brewed at a time.
- Brewing should be started asynchronously (do not block the API).
- Assign a unique ID to each batch. Use UUID.randomUUID() to generate the ID.
- If brewing is successfully started, return HTTP 202 Accepted and include ID.
- If brewing is already in progress for the given type, queue the request and return HTTP 202 Accepted.
- If brewing is already in progress for the given type and there is already 3 requests in the queue, return HTTP 429 Too Many Requests.
- Queued batches should automatically start brewing once the currently active batch for that beer type finishes, regardless of whether it completed successfully or spoiled.
- Batches that are rejected due to queue overflow (HTTP 429) must not be created or stored.

2. Check Brewing Status API
- Returns the list of all batches ever started for the given beer type.
- Batches must be retained in memory.
- Each batch should include: id, beerType, amountInLitres
- status: QUEUED, IN_PROGRESS, COMPLETED, or SPOILED

3. Handle Spoilage
- Assume the brewing service may randomly throw `IllegalStateException` to simulate spoilage.
- Spoiled batches must be tracked and shown in status API. 
- Handle spoilage gracefully — do not crash or remove spoiled batches silently.
