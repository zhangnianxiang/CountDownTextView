package com.bsnl.base.widget.countdowntv;

/**
 * @author bsnl_zhangnx
 * @date 2019/3/14 9:56
 * @desc
 */
public interface CDTVEnum {

    public long DAY = 86400;

    public enum Model {
        Merge(1), Solo(2);

        int model;

        private Model(int model) {
            this.model = model;
        }
    }

    public enum Orientation {

        Top(1), Center(2), Bottom(3);
        int code;

        private Orientation(int code) {
            this.code = code;
        }

    }


    public enum Show {

        All(1), Hide(2), Three(3);
        int show;

        private Show(int show) {
            this.show = show;
        }

    }


}
