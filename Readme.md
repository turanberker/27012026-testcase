<h1>Overview</h1>
<p>
The system is designed as three independent modules following scalability and separation of concerns principles:
</p>
<ul>
    <li>CommandService</li>
    <li>SchedulerService</li>
    <li>ConsumerService</li>
</ul>
Each module can be deployed independently and has a clearly defined responsibility.
<h1>CommandService</h1>
CommandService is the public-facing layer of the application.
<ul>
<li>Runs on port 8500 by default</li>
<li>Designed to run in multiple instances</li>
</ul>
<h2>Provided Feature</h2>
<ul>
<li>File Upload</li>

<li>Coupon Request</li>
<li>Coupon Usage</li>
</ul>
<h3>File Upload Flow</h3>
1.The user sends a file upload request

2.The file is stored in MinIO

3.File metadata is saved to the database for further processing

<h3> Coupon Request Flow</h3>

<ul>
<li>If the user specifies a coupon type, a coupon of that type is generated</li>
<li>If no type is specified, a STANDARD coupon is generated

<ul>
<li>MegaDeal Coupon Handling
<ul>
<li>For each request, the system checks for a LOCK in Redis</li>
<li>If no LOCK exists:
<ul>
<li>If the number of active TOKENs in Redis is less than 10:
<ul>
<li>A TOKEN with 1 second TTL is created in Redis</li>
<li>
An available coupon is returned to the user</li>
</ul>
</li>
</ul>
</li>
<li>
If the number of active TOKENs reaches 10:
<ul>
<li>A LOCK with 10 seconds TTL is created in Redis</li>
<li>Subsequent requests are throttled during this period</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>

<h2>Coupon Usage</h2>
<ul>
<li>If the user has an active and valid coupon:
The discount rate

The discount type
</li>
</ul>
are returned to the user.

<h1>SchedulerService</h1>
SchedulerService is responsible for background processing and is designed to run as a single instance.

<h2>Responsibilities</h2>
<ul>
<li>Processing uploaded files and persisting data into the coupon table</li>
<li>Removing expired and inactive unused coupons</li>
</ul>

<h2>Technical Details</h2>
Spring Batch is used for file processing operations
<h1>ConsumerService</h1>
ConsumerService acts as a Kafka consumer and persists incoming events into the database.

<b>Kafka Topics</b>

There are two Kafka topics in the system:

1. **Coupon statistics:** Tracks coupon usage and assignment counts by coupon type

2. **System logs:** Persists application-generated log events into the database


<h2>Logging Mechanism</h2>
A separate library named logging-aop was developed for centralized logging.

Methods annotated with @CouponLog:
<ul>
<li>Are intercepted via Aspect</li>
<li>Produce a log event</li>
<li>Publish the event to Kafka</li>
</ul>
<h2>Security Configuration (CommandService)</h2>
A dedicated security library was implemented for CommandService.
**Features**

1. Uses InMemoryUserDetailsService

2. Supports Basic Authentication

3. Fully configurable via application configuration
_SECURITY_ENABLED=true | false_


<h2>Behavior</h2>

**SECURITY_ENABLED = true**

1. Authentication is enabled

2. userId is retrieved from SecurityContextHolder

**SECURITY_ENABLED = false**

1. Security is disabled

2. userId must be provided explicitly in coupon usage and coupon request APIs








<h3>Build</h3>
You can run docker compose -f app-docker-compose.yml up -d --build to build and run all containers

<table>
<thead>
<td>Application</td>
<td>Build Command</td>
</thead>
<tbody>
<tr>
<td>Command Application</td>
<td>docker build -f .\command.Dockerfile -t coupon-command-server .</td>
</tr>
<tr>
<td>Consumer Application</td>
<td>docker build -f .\consumer.Dockerfile -t coupon-consumer-server .</td>
</tr>
<tr>
<td>Command Application</td>
<td>docker build -f .\scheduler.Dockerfile -t coupon-scheduler-server .</td>
</tr>
</tbody>
</table>


<h3>End Points</h3>
<table>
<thead>
<td>URL</td>
<td>Method</td>
<td>Request Body</td>
<td>Required Role</td>
</thead>
<tbody>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/upload</td>
<td>POST</td>

<td>form-data-->name file</td>
<td>ADMIN</td>
</tr>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/request</td>
<td>POST</td>
<td>{"userId":string * required,"couponType":"FREE|STANDART|MEGADEAL" }</td>
<td>USER</td>
</tr>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/redeem</td>
<td>PUT</td>
<td>{"userId":"string * required,"couponCode":string * required}</td>
<td>USER</td>
</tr>
</tbody>
</table>
Note: if SECURITY_ENABLED is true then you can leave userId fields as empty(Will be ignored).  




<h3>Users</h3>
<table>
<thead>
<td>UserName</td>
<td>Password</td>
<td>Role</td>
</thead>
<tbody>
<tr>
<td>admin</td>
<td>admin</td>
<td>ADMIN</td>
</tr>
<tr>
<td>user1</td>
<td>user</td>
<td>USER</td>
</tr>
<tr>
<td>user2</td>
<td>user</td>
<td>USER</td>
</tr>
</tbody>
</table>



<h3>Notes</h3>
<ul>
<li>
<I>Megadeal Coupon Limit Requirement</I>
<p>
The requirement stating that "New coupons can be requested at most 5 times, 
at the same time" has not been fully understood. It is interpreted to mean that a maximum of 5 Megadeal-type coupons can be active (ready for use) concurrently. In other words, at any given moment, the system should not allow more than 5 Megadeal coupons to exist in an active state. This ensures controlled distribution and prevents over-allocation of these special coupons.
</p>
</li>
</ul>
