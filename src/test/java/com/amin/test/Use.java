//package com.amin.test;
//
//import org.apache.batik.anim.dom.SVGDOMImplementation;
//import org.apache.batik.transcoder.TranscoderException;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscodingHints;
//import org.apache.batik.transcoder.image.ImageTranscoder;
//import org.apache.batik.util.SVGConstants;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * is created by aMIN on 1/11/2019 at 1:18 AM
// */
//public class Use {
//    public static void main(String[] args) throws IOException, TranscoderException {
//        String name = "/drawable/Spinner-1.2s-200px.svg";
//        URL resource = Use.class.getResource(name);
////        SvgImageLoader.loadSvg(resource, 400);
//
//        MyTranscoder transcoder = new MyTranscoder();
//        TranscodingHints hints = new TranscodingHints();
//        hints.put(ImageTranscoder.KEY_WIDTH, 20f); //your image width
//        hints.put(ImageTranscoder.KEY_HEIGHT, 20f); //your image height
//        hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION,    SVGDOMImplementation.getDOMImplementation());
//        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
//        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
//        hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);
//
//        transcoder.setTranscodingHints(hints);
//        TranscoderInput input = new TranscoderInput(resource.toExternalForm());
//        transcoder.transcode(input, null);
//        BufferedImage bufferedImage = transcoder.getImage();
//    }
//}
