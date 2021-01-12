/*
 * This is free software, licensed under the Gnu Public License (GPL)
 * get a copy from <http://www.gnu.org/licenses/gpl.html>
 */
package org.fakebelieve.henplus.plugins.rollback;

import henplus.*;
import henplus.event.ExecutionListener;

import java.sql.SQLException;

public final class RollbackCommand extends AbstractCommand {

    private static final String COMMAND_ROLLBACK = "set-rollback";
    public static final String PROPERTY_ROLLBACK = "rollback";

    /**
     *
     */
    public RollbackCommand() {
        registerRollbackUpdater(HenPlus.getInstance().getDispatcher());
    }

    public void registerRollbackUpdater(final CommandDispatcher dispatcher) {
        dispatcher.addExecutionListener(new ExecutionListener() {

            @Override
            public void beforeExecution(final SQLSession currentSession, final String command) {
                if (currentSession != null && !currentSession.getPropertyRegistry().getPropertyMap().containsKey(PROPERTY_ROLLBACK)) {
                    currentSession.getPropertyRegistry().registerProperty(PROPERTY_ROLLBACK, new RollbackProperty());
                }
            }

            @Override
            public void afterExecution(SQLSession currentSession, final String command, final int result) {
                currentSession = HenPlus.getInstance().getCurrentSession();

                if (currentSession != null) {
                    RollbackProperty property = (RollbackProperty) currentSession.getPropertyRegistry().getPropertyMap().get(PROPERTY_ROLLBACK);

                    if (property != null) {
                        if (property.getValue().equals(RollbackProperty.ROLLBACK_FAILURE) && result != Command.SUCCESS) {
                            HenPlus.getInstance().msg().println("ROLLBACK: Cause - Failed command.");
                            try {
                                currentSession.getConnection().rollback();
                            } catch (SQLException exception) {
                                // IGNORE
                            }
                        }
                    }
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#getCommandList()
     */
    @Override
    public String[] getCommandList() {
        return new String[]{COMMAND_ROLLBACK};
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#participateInCommandCompletion()
     */
    @Override
    public boolean participateInCommandCompletion() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#execute(henplus.SQLSession, java.lang.String, java.lang.String)
     */

    @Override
    public int execute(SQLSession session, String command, String parameters) {
        int result = SUCCESS;

        // required: session
        if (session == null) {
            HenPlus.msg().println("You need a valid session for this command.");
            return EXEC_FAILED;
        }

        if (command.equals(COMMAND_ROLLBACK)) {
            if (parameters == null || parameters.isEmpty()) {
            } else {
            }
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#isComplete(java.lang.String)
     */
    @Override
    public boolean isComplete(String command) {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#requiresValidSession(java.lang.String)
     */
    @Override
    public boolean requiresValidSession(String cmd) {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#shutdown()
     */
    @Override
    public void shutdown() {
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#getShortDescription()
     */
    @Override
    public String getShortDescription() {
        return "show/customize command prompt";
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#getSynopsis(java.lang.String)
     */
    @Override
    public String getSynopsis(String cmd) {
        return COMMAND_ROLLBACK + " " + " <prompt>";
    }

    /*
     * (non-Javadoc)
     * @see henplus.Command#getLongDescription(java.lang.String)
     */
    @Override
    public String getLongDescription(String cmd) {
        return "\tView or customize the command prompt\n"
                + "\n"
                + "\tTo view current prompt\n"
                + "\t\t" + COMMAND_ROLLBACK + ";\n"
                + "\tTo change the prompt\n"
                + "\t\t" + COMMAND_ROLLBACK + " <prompt>;\n"
                + "\n";
    }

}
