package com.feed.news.controller;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
public class Paginator {



    List<Integer> getPaginator(int pageNumber, int totalPages){
        List<Integer> head = (pageNumber > 4)? new ArrayList<>(Arrays.asList(1,-1)) :new ArrayList<>(Arrays.asList(1,2,3));
        List<Integer> tail = (pageNumber < totalPages - 3)?new ArrayList<>(Arrays.asList(-1,totalPages)):new ArrayList<>(Arrays.asList(totalPages-2,totalPages-1,totalPages));
        List<Integer> bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?new ArrayList<>(Arrays.asList(pageNumber - 2, pageNumber - 1)):new ArrayList<>();
        List<Integer> bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?new ArrayList<>(Arrays.asList(pageNumber + 1, pageNumber + 2)):new ArrayList<>();

        log.info(head);
        log.info(bodyBefore);
        log.info(bodyAfter);
        log.info(tail);

        Optional.ofNullable(bodyBefore).ifPresent(head::addAll);
        head.addAll((pageNumber > 3 && pageNumber < totalPages - 2)?new ArrayList<>(Arrays.asList(pageNumber)):new ArrayList<>());
        Optional.ofNullable(bodyAfter).ifPresent(head::addAll);
        Optional.ofNullable(tail).ifPresent(head::addAll);

        return head;



    }

}
