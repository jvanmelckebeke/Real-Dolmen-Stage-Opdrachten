/*
 * Copyright (c) 2017. MIT-license for Jari Van Melckebeke
 * Note that there was a lot of educational work in this project,
 * this project was (or is) used for an assignment from Realdolmen in Belgium.
 * Please just don't abuse my work
 */

define(function (require) {

    require('./marker/MarkLineModel');
    require('./marker/MarkLineView');

    require('../echarts').registerPreprocessor(function (opt) {
        // Make sure markLine component is enabled
        opt.markLine = opt.markLine || {};
    });
});
