import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import TaskDetails from "../TaskDetails/TaskDetails";

const Home = ({ isAuthenticated, userName, accessToken }) => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedTask, setSelectedTask] = useState(null);
  const [editingTask, setEditingTask] = useState(null);
  const [notification, setNotification] = useState(null);
  const [creatingTask, setCreatingTask] = useState(false);
  const [newTaskData, setNewTaskData] = useState({
    taskName: "",
    description: "",
  });

  const navigate = useNavigate();

  const fetchTasks = async () => {
    try {
      // Make a request to the protected endpoint with the accessToken
      console.log(accessToken);
      const response = await axios.get("http://localhost:8080/api/task/all", {
        // method: 'GET',
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      setTasks(response.data);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    // Check if the user is authenticated before fetching tasks
    if (isAuthenticated) {
      fetchTasks();
    } else {
      setLoading(false);
    }
  }, [isAuthenticated, accessToken]);

  const handleCheck = async (taskId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/task/${taskId}`,
        {
          // method: 'GET',
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      console.log("Check response:", response.data);
      setSelectedTask(response.data);
    } catch (error) {
      console.error("Error checking task:", error);
    }
    console.log(`Checking task ${taskId}`);
  };

  const handleUpdate = async (taskId, updatedTask) => {
    // Handle the "Update" action for the task with taskId
    const response = await axios.put(
      `http://localhost:8080/api/task/update/${taskId}`,
      updatedTask,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );
    if (response.ok || response.status == 202) {
      console.log("Update response:", response.data);
      setSelectedTask(null);
      handleRefresh();
      // Optionally, you can update the local state or refetch tasks
    } else {
      console.error("Update failed:", response.status, response.statusText);
    }

    console.log(`Updating task ${taskId}`);
  };

  const handleDelete = async (taskId) => {
    // Handle the "Delete" action for the task with taskId
    try {
      // Make a DELETE request to the delete endpoint
      const response = await axios.delete(
        `http://localhost:8080/api/task/delete/${taskId}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      if (response.status >= 200 && response.status < 300) {
        console.log(`Deleting task ${taskId}`);
        console.log("Delete successful");
        handleRefresh(); // Optionally, you can refresh the task list after deletion
      } else {
        console.error("Delete failed:", response.status, response.statusText);
      }
    } catch (error) {
      console.error("Error deleting task:", error);
    }
  };

  const handleEdit = (task) => {
    setEditingTask(task);
  };

  const handleCancelEdit = () => {
    setEditingTask(null);
  };

  const handleFormSubmit = (taskId, updatedTask) => {
    handleUpdate(taskId, updatedTask);
    setEditingTask(null);
  };

  const handleRefresh = () => {
    // Add logic here to refresh the tasks
    fetchTasks();
  };

  const handleCreateTask = () => {
    setCreatingTask(true);
  };
  const handleCancelCreateTask = () => {
    setCreatingTask(false);
    setNewTaskData({
      taskName: "",
      description: "",
    });
  };
  const handleNewTaskChange = (e) => {
    const { name, value } = e.target;
    setNewTaskData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const handleCreateTaskSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        `http://localhost:8080/api/task/create`,
        newTaskData,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status >= 200 && response.status <= 300) {
        console.log("Task created successfully", response.data);
        fetchTasks();
        // Reset newTaskData after successful task creation
        setNewTaskData({
          taskName: "",
          description: "",
        });
        // Hide the create task form
        setCreatingTask(false);
      } else {
        console.error(
          "Task creation failed:",
          response.status,
          response.statusText
        );
      }
    } catch (error) {
      console.error("Error creating task:", error);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">
        {isAuthenticated ? `Welcome, ${userName}!` : "Welcome!"}
      </h1>
      {isAuthenticated && (
        <button
          onClick={handleRefresh}
          className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue"
        >
          Refresh
        </button>
      )}
      {isAuthenticated && (
        <button
          onClick={handleCreateTask}
          className="bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600 focus:outline-none focus:shadow-outline-green"
        >
          Create New Task
        </button>
      )}
      {creatingTask && (
        <div className="max-w-md mx-auto p-8">
          <h2 className="text-2xl font-bold mb-4">Create New Task</h2>
          <form
            onSubmit={(e) => handleCreateTaskSubmit(e)}
            className="space-y-4"
          >
            <div>
              <label
                htmlFor="taskName"
                className="block text-sm font-medium text-gray-700"
              >
                Task Name:
              </label>
              <input
                type="text"
                id="taskName"
                name="taskName"
                value={newTaskData.taskName}
                onChange={handleNewTaskChange}
                className="mt-1 p-2 border rounded-md w-full"
                required
              />
            </div>
            <div>
              <label
                htmlFor="description"
                className="block text-sm font-medium text-gray-700"
              >
                Description:
              </label>
              <input
                type="text"
                id="description"
                name="description"
                value={newTaskData.description}
                onChange={handleNewTaskChange}
                className="mt-1 p-2 border rounded-md w-full"
                required
              />
            </div>
            <div className="flex items-center">
              <button
                type="submit"
                className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue"
              >
                Create Task
              </button>
              <button
                type="button"
                onClick={handleCancelCreateTask}
                className="ml-2 bg-gray-500 text-white py-2 px-4 rounded-md hover:bg-gray-600 focus:outline-none focus:shadow-outline-gray"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}
      {loading && <p>Loading tasks...</p>}

      {!isAuthenticated && !loading && (
        <p className="text-gray-600">
          This is the home page content accessible to all users. Login to check
          your tasks.
        </p>
      )}
      {!isAuthenticated && !loading && (
        <div className="text-center mt-8">
          <p className="text-2xl font-bold text-gray-800 mb-4">
            Welcome to My Task Management App!
          </p>
          <p className="text-lg text-gray-600 mb-6">
            Organize your tasks efficiently and stay productive with our task
            management app. Sign in to manage your tasks and unlock additional
            features.
          </p>
        </div>
      )}

      {isAuthenticated && tasks.length > 0 && (
        <div>
          <h2 className="text-xl font-bold mb-2">Your Tasks:</h2>
          <ul>
            {tasks.map((task) => (
              <li key={task.taskId} className="mb-2">
                {editingTask && editingTask.taskId === task.taskId ? (
                  // Display form for editing
                  <div className="flex items-center space-x-2">
                    <input
                      type="text"
                      value={editingTask.taskName}
                      onChange={(e) =>
                        setEditingTask({
                          ...editingTask,
                          taskName: e.target.value,
                        })
                      }
                      className="border rounded p-2"
                    />
                    <input
                      type="text"
                      value={editingTask.description}
                      onChange={(e) =>
                        setEditingTask({
                          ...editingTask,
                          description: e.target.value,
                        })
                      }
                      className="border rounded p-2"
                    />
                    <input
                      type="text"
                      value={editingTask.taskStatus}
                      onChange={(e) =>
                        setEditingTask({
                          ...editingTask,
                          taskStatus: e.target.value,
                        })
                      }
                      className="border rounded p-2"
                    />
                    <button
                      onClick={() => handleFormSubmit(task.taskId, editingTask)}
                      className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue"
                    >
                      Submit
                    </button>
                    <button
                      onClick={handleCancelEdit}
                      className="bg-gray-500 text-white py-2 px-4 rounded-md hover:bg-gray-600 focus:outline-none focus:shadow-outline-gray"
                    >
                      Cancel
                    </button>
                  </div>
                ) : (
                  // Display task details
                  <div className="flex items-center space-x-2">
                    <span className="font-semibold">{task.taskName}</span>
                    <button
                      onClick={() => handleCheck(task.taskId)}
                      className="bg-green-500 text-white py-1 px-2 rounded-md hover:bg-green-600 focus:outline-none focus:shadow-outline-green"
                    >
                      Check
                    </button>
                    <button
                      onClick={() => handleEdit(task)}
                      className="bg-yellow-500 text-white py-1 px-2 rounded-md hover:bg-yellow-600 focus:outline-none focus:shadow-outline-yellow"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(task.taskId)}
                      className="bg-red-500 text-white py-1 px-2 rounded-md hover:bg-red-600 focus:outline-none focus:shadow-outline-red"
                    >
                      Delete
                    </button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}
      {isAuthenticated && (
        /* Render the TaskDetails component with the selected task */
        <TaskDetails task={selectedTask} onCancel={() => setSelectedTask(null)} />
      )}
    </div>
  );
};

export default Home;
