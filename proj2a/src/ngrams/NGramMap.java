package ngrams;

import edu.princeton.cs.algs4.In;

import javax.xml.stream.events.EndDocument;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    // TODO: Add any necessary static/instance variables.
    private HashMap<String, TimeSeries> wordMap;     // 存储每个单词的历史数据：Map<单词, 时间序列>
    private TimeSeries cntTS;                        // 存储每年所有书的总词数：这是一个单独的时间序列

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        this.wordMap = new HashMap<>();
        this.cntTS = new TimeSeries();

        In cntRead = new In(countsFilename);
        while (cntRead.hasNextLine()){
            String line = cntRead.readLine();
            String[] splitLine = line.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double totalCnt = Double.parseDouble(splitLine[1]);

            cntTS.put(year, totalCnt);
        }
        cntRead.close();

        In wordRead = new In(wordsFilename);
        while (wordRead.hasNextLine()){
            String word = wordRead.readString();
            int year = wordRead.readInt();
            double cnt = wordRead.readDouble();
            wordRead.readLine();

            if (!this.wordMap.containsKey(word)){
                this.wordMap.put(word, new TimeSeries());
            }

            TimeSeries ts = this.wordMap.get(word);
            ts.put(year, cnt);
        }
        wordRead.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!wordMap.containsKey(word)){
            return new TimeSeries();
        }
        TimeSeries data = wordMap.get(word);
        return new TimeSeries(data, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(cntTS, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!wordMap.containsKey(word)){
            return new TimeSeries();
        }
        return countHistory(word, startYear, endYear).dividedBy(totalCountHistory());
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries summedTS = new TimeSeries();
        for (String word : words){
            if (!wordMap.containsKey(word)){
                continue;
            }
            TimeSeries ts = weightHistory(word, startYear, endYear);
            summedTS = summedTS.plus(ts);
        }
        return summedTS;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
