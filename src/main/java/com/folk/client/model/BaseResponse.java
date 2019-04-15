package com.folk.client.model;

import com.folk.server.model.BaseModel;

import java.util.List;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
public class BaseResponse<T extends BaseModel> {
    public List<T> content;
    public int totalPages;
    public int totalElements;
    public int size;
    public int number;
}
