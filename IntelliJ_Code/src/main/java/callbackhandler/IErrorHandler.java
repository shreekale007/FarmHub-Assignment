package com.farm.hub.callbackhandler;

/**
 * Interface to provide error occurred callback while performing task execution.
 */
public interface IErrorHandler {
    /**
     * Callback provided when the execution of assigned task is encounter some error.
     *
     * @param errorMessage errorMessage to be passed to the subscriber on completion of task
     */
    void onErrorOccurred(String errorMessage);
}
