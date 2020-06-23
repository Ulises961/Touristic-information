package com.OpenDataHub.fileio;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This interface is used to decorate a class which makes it usable in the JsonFile class.
 */
public interface FileWritable {
    /**
     * The filename which it will have in the filesystem without extension
     * @return
     */
    @JsonIgnore
    String getFileId();
}
