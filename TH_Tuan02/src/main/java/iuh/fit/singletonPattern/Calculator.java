package iuh.fit.singletonPattern;

public class Calculator {
    private static Calculator instance;
    private  double result=0;

    private Calculator(){}

    public static Calculator getInstance(){
        if (instance==null){
            instance= new Calculator();
        }
        return instance;
    }
    public void clear(){result =0;}
    public void add(double a){
        result+=a;
    }
    public void subtract(double a){
        result-=a;
    }
    public void multiply(double a){
        result*=a;
    }
    public void divide(double a){
        if(a!=0) result/=a;
        else{
            System.out.println("Lỗi: Không thể chia cho 0!");
        }
    }
    public void showInfo(){
        System.out.println("Calculator: " + result);
    }
    public double getResult(){
        return result;
    }
}
