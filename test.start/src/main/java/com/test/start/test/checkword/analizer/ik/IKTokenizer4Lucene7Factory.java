package com.test.start.test.checkword.analizer.ik;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import java.util.Map;

public class IKTokenizer4Lucene7Factory extends TokenizerFactory {

    private boolean useSmart = false;

    public IKTokenizer4Lucene7Factory(Map<String, String> args) {
        super(args);
        String useSmartParm = args.get("useSmart");
        if ("true".equalsIgnoreCase(useSmartParm)) {
            this.useSmart = true;
        }
    }

    @Override
    public Tokenizer create(AttributeFactory attributeFactory) {
        return new IKTokenizer4Lucene7(this.useSmart);
    }
}
