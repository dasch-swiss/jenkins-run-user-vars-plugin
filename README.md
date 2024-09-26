# Jenkins Run User Vars Plugin

This Jenkins plugin extends [Build User Vars](https://plugins.jenkins.io/build-user-vars-plugin/) so that the run user (i.e. user the run is running under) information is also added to the environment.

### Usage

Enable build user vars globally or use the `RunUser` build wrapper just like the `BuildUser` wrapper in the Build User Vars plugin.

The following environment variables are added:

| Variable | Description |
|----------|-------------|
| RUN_USER | Full name (first name + last name) |
| RUN_USER_FIRST_NAME | First name |
| RUN_USER_LAST_NAME | Last name |
| RUN_USER_ID | Jenkins user ID |
| RUN_USER_GROUPS | Jenkins user groups |
| RUN_USER_EMAIL | Email address |

### Development

Starting a development Jenkins instance with this plugin: `mvn hpi:run`

Building the plugin: `mvn package`
