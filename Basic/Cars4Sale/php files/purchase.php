<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Details Processed</title>
  <link rel="stylesheet" href="../Css/style1.css">
</head>
<body>
  <header>
    <h1>User Details Processed</h1>
  </header>
  <main>
    <?php
      $servername = "localhost";
      $username = "sin12559_car_user";
      $password = "Sagandeep@0268";
      $dbname = "sin12559_car_inventory_db";

      $conn = new mysqli($servername, $username, $password, $dbname);

      if ($conn->connect_error) {
          die("Connection failed: " . $conn->connect_error);
      }

      if ($_SERVER["REQUEST_METHOD"] == "POST") {
          $carId = $_POST["car_id"];
          $userName = $_POST["name"];
          $userEmail = $_POST["email"];
          $userMobile = $_POST["mobile"];

          $sql = "INSERT INTO Data (car_id, name, email, mobile) VALUES ('$carId', '$userName', '$userEmail', '$userMobile')";

          if ($conn->query($sql) === TRUE) {
              echo "Purchase successfull !!";
              echo "<br><a class='back-link' href='display.php'>View orders</a>";
          } else {
              echo "Error inserting user data: " . $conn->error;
          }
      } else {
          header("Location: index.html");
          exit();
      }

      $conn->close();
    ?>
  </main>
  <footer>
    <p>&copy; Used-Car Inventory</p>
  </footer>
</body>
</html>


