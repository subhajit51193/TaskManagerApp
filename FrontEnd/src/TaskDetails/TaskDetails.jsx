
import React from 'react';

const TaskDetails = ({ task, onCancel }) => {
  return (
    <div className="mt-4">
      <h2 className="text-xl font-bold mb-2">Task Details:</h2>
      {task ? (
        <table className="min-w-full border border-gray-300">
          <tbody>
            <tr>
              <td className="border px-4 py-2 font-semibold">Task ID</td>
              <td className="border px-4 py-2">{task.taskId}</td>
            </tr>
            <tr>
              <td className="border px-4 py-2 font-semibold">Task Name</td>
              <td className="border px-4 py-2">{task.taskName}</td>
            </tr>
            <tr>
              <td className="border px-4 py-2 font-semibold">Task Description</td>
              <td className="border px-4 py-2">{task.description}</td>
            </tr>
            <tr>
              <td className="border px-4 py-2 font-semibold">Task Status</td>
              <td className="border px-4 py-2">{task.taskStatus}</td>
            </tr>
          </tbody>
        </table>
      ) : (
        <p>No task selected.</p>
      )}
      {/* Conditionally render the Cancel button */}
      {task && (
        <button
          onClick={onCancel}
          className="bg-gray-500 text-white py-2 px-4 mt-4 rounded-md hover:bg-gray-600 focus:outline-none focus:shadow-outline-gray"
        >
          Cancel
        </button>
      )}
    </div>
  );
};

export default TaskDetails;
