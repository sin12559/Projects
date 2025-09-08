const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const connectDB = require('./dbConnector');
const taskRoutes = require('./taskRoutes');

dotenv.config();
connectDB();

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api/tasks', taskRoutes);

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`🚀 Server running on port ${PORT}`));
