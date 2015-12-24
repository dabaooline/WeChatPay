package net.sourceforge.simcpux.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2015/12/16.
 */
public class XmlUtils {


//    k {kv,kv,kv}kv{k{k{kv,kv,kv}k{kv,kv,kv}}}

    /**
     * 仅用于简单的xml格式
     * @param xml
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static Element xmlParser(InputStream xml) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xml);
        Element root = document.getRootElement();
        return root;
    }
}
