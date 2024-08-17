<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pharmacy Inventory Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        header {
            background: #333;
            color: #fff;
            padding-top: 30px;
            min-height: 70px;
            border-bottom: #bbb 1px solid;
            text-align: center;
        }
        header h1 {
            margin: 0;
            font-size: 24px;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
        }
        code {
            background: #eee;
            padding: 5px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <h1>Pharmacy Inventory Management System</h1>
        </div>
    </header>

    <div class="container">
        <section>
            <h2>Database Overview</h2>
            <p>This database is designed using MySQL and includes the following tables:</p>
            <ul>
                <li>drugs</li>
                <li>purchases</li>
                <li>reports</li>
                <li>suppliers</li>
                <li>users</li>
            </ul>
        </section>

        <section>
            <h2>Table Structures</h2>

            <h3>1. Drugs Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>Column Name</th>
                        <th>Data Type</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>drugId</td>
                        <td>int(15)</td>
                        <td>Unique identifier for the drug</td>
                    </tr>
                    <tr>
                        <td>name</td>
                        <td>varchar(255)</td>
                        <td>Name of the drug</td>
                    </tr>
                    <tr>
                        <td>category</td>
                        <td>varchar(255)</td>
                        <td>Category of the drug</td>
                    </tr>
                    <tr>
                        <td>quantity</td>
                        <td>int(255)</td>
                        <td>Quantity available in stock</td>
                    </tr>
                    <tr>
                        <td>price</td>
                        <td>int(255)</td>
                        <td>Price per unit of the drug</td>
                    </tr>
                </tbody>
            </table>
            <p><strong>Primary Key:</strong> drugId</p>

            <h3>2. Purchases Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>Column Name</th>
                        <th>Data Type</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>quantity</td>
                        <td>varchar(255)</td>
                        <td>Quantity purchased</td>
                    </tr>
                    <tr>
                        <td>drugId</td>
                        <td>int(15)</td>
                        <td>Foreign key to <code>drugs</code> table</td>
                    </tr>
                    <tr>
                        <td>totalAmount</td>
                        <td>int(255)</td>
                        <td>Total amount for the purchase</td>
                    </tr>
                    <tr>
                        <td>purchaseDate</td>
                        <td>timestamp</td>
                        <td>Date and time of purchase</td>
                    </tr>
                </tbody>
            </table>
            <p><strong>Primary Key:</strong> drugId</p>

            <h3>3. Reports Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>Column Name</th>
                        <th>Data Type</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>drugId</td>
                        <td>int(15)</td>
                        <td>Foreign key to <code>drugs</code> table</td>
                    </tr>
                    <tr>
                        <td>quantity</td>
                        <td>int(255)</td>
                        <td>Quantity reported</td>
                    </tr>
                    <tr>
                        <td>totalAmount</td>
                        <td>int(255)</td>
                        <td>Total amount reported</td>
                    </tr>
                    <tr>
                        <td>purchaseDate</td>
                        <td>timestamp</td>
                        <td>Date and time of report</td>
                    </tr>
                </tbody>
            </table>
            <p><strong>Primary Key:</strong> drugId</p>

            <h3>4. Suppliers Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>Column Name</th>
                        <th>Data Type</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>supplierId</td>
                        <td>int(15)</td>
                        <td>Unique identifier for the supplier</td>
                    </tr>
                    <tr>
                        <td>name</td>
                        <td>varchar(255)</td>
                        <td>Name of the supplier</td>
                    </tr>
                    <tr>
                        <td>contact</td>
                        <td>varchar(255)</td>
                        <td>Contact information of the supplier</td>
                    </tr>
                    <tr>
                        <td>location</td>
                        <td>varchar(255)</td>
                        <td>Location of the supplier</td>
                    </tr>
                </tbody>
            </table>
            <p><strong>Primary Key:</strong> supplierId</p>

            <h3>5. Users Table</h3>
            <table>
                <thead>
                    <tr>
                        <th>Column Name</th>
                        <th>Data Type</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>id</td>
                        <td>int(11)</td>
                        <td>Unique identifier for the user</td>
                    </tr>
                    <tr>
                        <td>username</td>
                        <td>varchar(50)</td>
                        <td>Username of the user</td>
                    </tr>
                    <tr>
                        <td>password</td>
                        <td>varchar(255)</td>
                        <td>Password for the user</td>
                    </tr>
                    <tr>
                        <td>created_at</td>
                        <td>datetime</td>
                        <td>Timestamp of user creation</td>
                    </tr>
                </tbody>
            </table>
            <p><strong>Primary Key:</strong> id<br><strong>Unique Key:</strong> username</p>
        </section>

        <section>
            <h2>Data Insertion</h2>
            <p>The database includes initial data for each table to provide a starting point for the system. Data for each table is inserted using the provided <code>INSERT INTO</code> statements.</p>
        </section>

        <section>
            <h2>Indexes and Auto-Increment</h2>
            <p><strong>Indexes</strong> are created on primary keys to ensure fast data retrieval.</p>
            <p><strong>Auto-Increment</strong> settings are applied to primary key columns to automatically generate unique values.</p>
        </section>

        <section>
            <h2>Character Set and Collation</h2>
            <p>The database uses the <code>utf8mb4</code> character set and <code>utf8mb4_general_ci</code> collation to support a wide range of characters and ensure proper sorting and comparison.</p>
        </section>

        <section>
            <h2>Installation and Setup</h2>
            <ol>
                <li><strong>Create the Database:</strong>
                    <pre><code>CREATE DATABASE `java` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;</code></pre>
                </li>
                <li><strong>Use the Database:</strong>
                    <pre><code>USE `java`;</code></pre>
                </li>
                <li><strong>Run the SQL Scripts:</strong>
                    <p>Execute the provided SQL script to create tables, insert data, and set up indexes and auto-increment values.</p>
                </li>
            </ol>
        </section>

        <section>
            <p><strong>Created By:</strong> Muhammed, Database Administrator</p>
        </section>
    </div>
</body>
</html>




<img src="Screenshot from 2024-07-08 00-54-51.png" class="rounded mx-auto d-block" alt="..."> 

<img src="Screenshot from 2024-07-08 00-55-05.png" class="rounded mx-auto d-block" alt="...">

<img src="Screenshot from 2024-07-08 01-31-17.png" class="rounded mx-auto d-block" alt="...">

<img src="Screenshot from 2024-07-08 01-31-36.png" class="rounded mx-auto d-block" alt="...">

<img src="Screenshot from 2024-07-08 01-31-48.png" class="rounded mx-auto d-block" alt="...">

<img src="Screenshot from 2024-07-08 01-31-58.png" class="rounded mx-auto d-block" alt="...">

<img src="Screenshot from 2024-07-08 01-32-18.png" class="rounded mx-auto d-block" alt="...">

