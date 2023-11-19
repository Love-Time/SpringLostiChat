package com.example.demo.dto.dialog;

import com.example.demo.dto.common.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DialogListPageResponse {
    Page page;
    List<DialogDto> data;
}
