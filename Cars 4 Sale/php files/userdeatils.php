<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Details</title>
  <link rel="stylesheet" href="../Css/style1.css">
</head>
<body>
  <header>
    <h1>User Details</h1>
  </header>
  <main>
    <?php
      $carId = $_GET["car_id"] ?? null;

      if ($carId === null) {
        header("Location: index.html");
        exit();
      }
    ?>

    <h2>Enter Your Details</h2>
    <form action="purchase.php" method="POST">
      <input type="hidden" name="car_id" value="<?php echo $carId; ?>">
      <label for="name">Your Name:</label>
      <input type="text" id="user_name" name="name" required>

      <label for="email">Your Email:</label>
      <input type="email" id="email" name="email" required>

      <label for="mobile">Your Mobile Number:</label>
      <input type="tel" id="user_mobile" name="mobile" required>

      <input type="submit" value="Submit">
    </form>
  </main>
  <footer>
    <p>&copy; Used-Car Inventory</p>
  </footer>
</body>
</html





