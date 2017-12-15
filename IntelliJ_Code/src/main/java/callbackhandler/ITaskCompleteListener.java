package com.farm.hub.callbackhandler;

import com.farm.hub.bussinesslogic.TaskType;

/**
 * Interface to provide task completion callback.
 */
public interface ITaskCompleteListener {
    /**
     * Callback provided when the execution of assigned task is completed.
     *
     * @param taskType type of the taks assigned
     * @param successMessage message to be passed to the subscriber on completion of task
     */
    void onTaskCompletion(TaskType taskType, String successMessage);
}
