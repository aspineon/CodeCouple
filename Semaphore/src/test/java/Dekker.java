public class Dekker extends Thread{
 
    private String name;
    private static int favoreprocess=1;
    private volatile boolean p1=false, p2=false;

    public static void main(String[] args) {
        Dekker x = new Dekker("X");
        Dekker y = new Dekker("Y");
        x.start();
        y.start();
    }
 
    public Dekker(String name){
        this.name=name;
    }
 
    @Override
    public void run(){
        try {
            if (name.equals("X")) {
                Thread.sleep(10);
            } else if (name.equals("Y")) {
                Thread.sleep(20);
            }
 
            if (name.equals("X")) 
            {
                while (true) 
                {
                    System.out.println("Proces P1 jest po za sekcją krytyczną");
                    sleep (1000);
                    System.out.println("Proces P1 chce wejść do sekcji krytycznej");
                    p1 = true;
                    while (p2) {
                        if (favoreprocess == 2) {
                            p1 = false;
                            while (favoreprocess == 2) ;
                            p1 = true;
                        }
                    }
                    System.out.println("Proces P1 jest w sekcji krytycznej");
                    System.out.println("----------------------------------");
                    sleep (1000);
 
                    favoreprocess = 2;
                    System.out.println("Proces P1 jest po za sekcją krytyczną");
                    System.out.println("----------------------------------");
                    sleep (100);
                    p1 = false;
                }
            }
 
            else if (name.equals("Y")) 
            {
                while (true) 
                {
                    System.out.println("Proces P2 jest po za sekcją krytyczną");
                    sleep (1000);
                    System.out.println("Proces P2 chce wejść do sekcji krytycznej");
                    p2 = true;
                    while (p1) {
                        if (favoreprocess == 1) {
                            p2 = false;
                            while (favoreprocess == 1) ;
                            p2 = true;
                        }
                    }
                    System.out.println("Proces P2 jest w sekcji krytycznej");
                    System.out.println("----------------------------------");
                    sleep (1000);
 
                    favoreprocess = 1;
                    System.out.println("Proces P2 jest po za sekcją krytyczną");
                    System.out.println("----------------------------------");
                    sleep (100);
                    p2 = false;
                }
            }
 
        } catch (InterruptedException ex) {}
    }
}