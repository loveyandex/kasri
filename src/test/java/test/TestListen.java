package test;

public class TestListen   {
    public static void main(String[] args) {
        Listen listen=new Listen();
        listen.listen(new Mylistener() {
            @Override
            public void onMylistner(String event) {
                System.out.println("event is >>>> "+event);
            }
        });


    }
}
