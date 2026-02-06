package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryHandler(NGramMap map){
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        //获取查询词和范围
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        //准备标签列表和时间序列列表
        List<String> labels = new ArrayList<>();
        List<TimeSeries> lts = new ArrayList<>();

        for (String word : words){
            TimeSeries ts = ngm.weightHistory(word, startYear, endYear);

            if (!ts.isEmpty()){
                labels.add(word);
                lts.add(ts);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
