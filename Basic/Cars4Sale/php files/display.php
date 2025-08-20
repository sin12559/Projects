<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Display User Data</title>
  <link rel="stylesheet" href="../Css/style1.css">
</head>
<body>
  <header>
    <h1>Display User Data</h1>
  </header>
  <main>
    <?php
    $servername = "localhost";
    $username = "sin12559_car_user";
    $password = "Sagandeep@0268";
    $dbname = "sin12559_car_inventory_db";

    $conn = new mysqli($servername, $username, $password, $dbname);

    $sql = "SELECT car_id, name, email, mobile FROM Data";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        echo "<table border='1'>";
        echo "<tr><th>Car ID</th><th>User Name</th><th>User Email</th><th>User Mobile</th></tr>";

        while ($row = $result->fetch_assoc()) {
            echo "<tr>";
            echo "<td>" . $row["car_id"] . "</td>";
            echo "<td>" . $row["name"] . "</td>";
            echo "<td>" . $row["email"] . "</td>";
            echo "<td>" . $row["mobile"] . "</td>";
            echo "</tr>";
        }

        echo "</table>";
    } else {
        echo "No user data found.";
    }

    $conn->close();
    ?>
  </main>
  <footer>
    <p>&copy; Used-Car Inventory</p>
  </footer>
</body>
</html>


