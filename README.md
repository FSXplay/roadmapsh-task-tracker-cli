# Task Tracker CLI

A simple command-line interface (CLI) application to track and manage your tasks.

This project allows you to:

- Add, update, and delete tasks
- Mark tasks as `todo`, `in-progress`, or `done`
- List tasks by status
- Store all tasks in a JSON file locally

This project was built as part of the Task Tracker challenge on roadmap.sh.

## Features
- Add new tasks
- Update existing tasks
- Delete tasks
- Mark tasks as todo/in-progress/done
- List all tasks or filter them by status
- Persistent storage using a local JSON file

## Requirements
- Java 17 or later.
- Windows OS

## Task Properties

Each task contains:

| Property     | Description |
|--------------|------------|
| `id`         | Unique task identifier |
| `description`| Task description |
| `status`     | `todo`, `in-progress`, or `done` |
| `createdAt`  | Date & time task was created |
| `updatedAt`  | Date & time task was last updated |

## Example Workflow
```bash
.\task-cli.bat add "Finish project"
.\task-cli.bat mark-in-progress 1
.\task-cli.bat list
.\task-cli.bat mark-done 1
.\task-cli.bat list done
```

## License
This project is for learning purposes and part of the roadmap.sh challenge.
