package com.example.linsa.retrofitdemo.service;

import com.example.linsa.retrofitdemo.bean.MovieDirector;
import com.example.linsa.retrofitdemo.util.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linsa on 2017/8/17:22:50.
 * des:
 */

public class DataService {


    /**
     * 返回多种数据的格式
     * @return
     */
    public static List<MovieDirector> getDataDirector(){

        ArrayList<MovieDirector> lists=new ArrayList<>();

        MovieDirector movieDirector=new MovieDirector();
        movieDirector.type= Common.MOVIE_DIRECT;
        movieDirector.name="Yang";
        movieDirector.age="19";
        lists.add(movieDirector);

        MovieDirector movieDirector1;
        for (int i = 0; i < 8; i++) {
            movieDirector1 = new MovieDirector();
            movieDirector1.type=Common.MOVIE;
            movieDirector1.name="OneX"+i;
            movieDirector1.age="1"+i;
            lists.add(movieDirector1);
        }

        MovieDirector movieDirector3=new MovieDirector();
        movieDirector3.type= Common.MOVIE_DIRECT;
        movieDirector3.name="曹操";
        movieDirector3.age="21";
        lists.add(movieDirector3);


        for (int i = 0; i < 8; i++) {
            MovieDirector movieDirector4=new MovieDirector();
            movieDirector4.type=Common.MOVIE;
            movieDirector4.name="Linsa"+i;
            movieDirector4.age="8"+i;
            lists.add(movieDirector4);
        }


        return lists;
    }


}
