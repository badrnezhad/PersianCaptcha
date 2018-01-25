package controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

@Controller
@Scope(value = "request")
public class CaptchaController extends HttpServlet {

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public HttpServletResponse createCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //image width and height
        int width = 150;
        int height = 70;

        //characters
        char data[] = {'ض', 'ص', 'ث', 'ق', 'ف', 'غ',
                'ع', 'ه', 'خ', 'ح', 'ج', 'چ', 'ی', 'ش',
                'س', 'ب', 'ل', 'ا', 'ت', 'ک', 'گ', 'ن',
                'م', 'ظ', 'ط', 'ز', 'ر', 'ذ', 'د', 'و'
        };

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        //font setting
        Font font = new Font("Arial", Font.BOLD, 18);
        g2d.setFont(font);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        //background gradient
        GradientPaint gp = new GradientPaint(0, 0,
                Color.decode("#265A88"), 0, height / 2, Color.decode("#3379B6"), false);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        //font color
        g2d.setColor(new Color(255, 255, 255));

        //create captcha code
        String captcha = "";
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int index = Math.abs(r.nextInt()) % data.length;
            captcha += data[index];
        }

        //store captcha to session
        request.getSession().setAttribute("captcha", captcha);

        //drawing characters
        int x = 0;
        int y = 0;
        for (int i = 0; i < 5; i++) {
            x += 10 + (Math.abs(r.nextInt()) % 20);
            y = 20 + Math.abs(r.nextInt()) % 35;
            g2d.drawChars(captcha.toCharArray(), i, 1, x, y);
        }

        g2d.dispose();

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        os.close();

        return response;
    }
}
