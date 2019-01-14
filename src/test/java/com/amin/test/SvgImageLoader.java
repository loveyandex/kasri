//package com.amin.test;
//
//import static org.apache.batik.transcoder.SVGAbstractTranscoder.KEY_WIDTH;
//import static org.apache.batik.transcoder.XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT;
//import static org.apache.batik.transcoder.XMLAbstractTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI;
//import static org.apache.batik.transcoder.XMLAbstractTranscoder.KEY_DOM_IMPLEMENTATION;
//import static org.apache.batik.util.SVGConstants.SVG_NAMESPACE_URI;
//import static org.apache.batik.util.SVGConstants.SVG_SVG_TAG;
//
//import com.google.common.flogger.FluentLogger;
//
//import org.apache.batik.anim.dom.SVGDOMImplementation;
//import org.apache.batik.transcoder.TranscoderException;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.batik.transcoder.TranscodingHints;
//import org.apache.batik.transcoder.image.ImageTranscoder;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//
//import javax.annotation.concurrent.ThreadSafe;
//import javax.inject.Singleton;
//
///** Loads SVG images from disk. See https://en.wikipedia.org/wiki/Scalable_Vector_Graphics. */
//@Singleton
//@ThreadSafe
//public class SvgImageLoader {
//
//  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
//  /**
//   * Reads in an SVG image file and return it as a BufferedImage with the given width and a height
//   * where the original aspect ratio is preserved.
//   *
//   * @param url URL referencing the SVG image file, which is typically an XML file
//   * @param width width in pixels the returned BufferedImage should be
//   *
//   * @return a valid image representing the SVG file
//   * @throws IOException if the file cannot be parsed as valid SVG
//   */
//  public static BufferedImage loadSvg(URL url, float width) throws IOException {
//    SvgTranscoder transcoder = new SvgTranscoder();
//    transcoder.setTranscodingHints(getHints(width));
//    try {
//      TranscoderInput input = new TranscoderInput(url.openStream());
//      transcoder.transcode(input, null);
//    } catch (TranscoderException e) {
//      throw new IOException("Error parsing SVG file " + url, e);
//    }
//    BufferedImage image = transcoder.getImage();
//    logger.atInfo().log("Read '%s' SVG image from disk requested with width=%.1f, sized as %dx%d pixels.",
//        new File(url.getFile()).getName(), width, image.getWidth(), image.getHeight());
//    return image;
//  }
//
//  private static TranscodingHints getHints(float width) {
//    TranscodingHints hints = new TranscodingHints();
//    hints.put(KEY_DOM_IMPLEMENTATION, SVGDOMImplementation.getDOMImplementation());
//    hints.put(KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVG_NAMESPACE_URI);
//    hints.put(KEY_DOCUMENT_ELEMENT, SVG_SVG_TAG);
//    hints.put(KEY_WIDTH, width);
//    return hints;
//  }
//
//  private static class SvgTranscoder extends ImageTranscoder {
//
//    private BufferedImage image = null;
//
//    @Override
//    public BufferedImage createImage(int width, int height) {
//      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//      return image;
//    }
//
//    @Override
//    public void writeImage(BufferedImage img, TranscoderOutput out) {}
//
//    BufferedImage getImage() {
//      return image;
//    }
//  }
//}