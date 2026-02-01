<h3>End Points</h3>
<table>
<thead>
<td>URL</td>
<td>Method</td>
<td>Request Body</td>
</thead>
<tbody>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/upload</td>
<td>POST</td>

<td>form-data-->name file</td>
</tr>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/request</td>
<td>POST</td>
<td>{"couponType":"FREE|STANDART|MEGADEAL" }</td>
</tr>
<tr>
<td>{CommanServiceUrl}/{port}/coupon/redeem</td>
<td>PUT</td>
<td>{"couponCode":string }</td>
</tr>
</tbody>
</table>





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
