package com.fastshop.net.service;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class _GetListFile {
    public String[] get(String folder) {
        File directoryPath = new File(folder);
        return directoryPath.list();
    }
}
