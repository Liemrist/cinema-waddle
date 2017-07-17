package project.epam.com.cinemawaddle.tabitems;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.BaseView;

public interface BaseTabView<T> extends BaseView {

    void showMoviesSet(List<T> items);
}
