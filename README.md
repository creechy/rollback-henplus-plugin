## Rollback Henplus Plug-In ##

This is a simple plugin that attempts to rollback a transaction on any command failure. JDBC has the notion of auto-committing after every command but not really any converse of that, and some JDBC drivers, most notibly the Presto JDBC driver, doesn't support auto-commit. So if you attempt to use such driver with Henplus, you get into an annoying work cycle of manually having to `rollback` every syntax error, for example.

This plugin attempts to do that for you.

### Easy Setup ###

Simply put `rollback-henplus-plugin.jar` in to the CLASSPATH of `henplus`, generally in the `share/henplus` folder somewhere.

Start `henplus` and register the plugin. Use the `plug-in` command for this. This only needs to be done once, and will be persisted.

     Hen*Plus> plug-in org.fakebelieve.henplus.plugins.rollback.RollbackCommand

### Usage ###

When enabled, the plugin will automatically register a new session `rollback` session property for each connection you make. 

By default, it will be set to `off`, meaning, don't attempt to rollback on any error.

You can change the value to `failure`, after which the plugin will watch for errors after each command and attempt to automatically rollback the transaction.

    set-session-property rollback failure

You will be notified that this has happened via the message

    ROLLBACK: Cause - Failed command.
