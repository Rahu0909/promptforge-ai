package com.rahulagarwal.promptforge.vector.service;

import com.rahulagarwal.promptforge.vector.dto.request.SaveVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.request.SearchVectorRequest;
import com.rahulagarwal.promptforge.vector.dto.response.SaveVectorResponse;
import com.rahulagarwal.promptforge.vector.dto.response.SearchVectorResponse;

public interface VectorStoreService {

    SaveVectorResponse saveEmbedding(SaveVectorRequest request);

    SearchVectorResponse search(SearchVectorRequest request);

}