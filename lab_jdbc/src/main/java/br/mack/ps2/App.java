package br.mack.ps2;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Connection conn = null;
        Scanner entrada = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String db="lab_jdbc";
            String url="jdbc:mysql://localhost:32775/"+db;
            String user="root";
            String psw="root";

            conn=DriverManager.getConnection(url,user,psw);

            //MOSTAR BASE DE DADOS ATUAL
            String sql1 = "SELECT * FROM contas;";
            PreparedStatement pstm = conn.prepareStatement(sql1);
            ResultSet rs = pstm.executeQuery();
            int ct=0;
            System.out.println("BASE DE DADOS ATUAL:");
            while (rs.next()){
                long conta = rs.getLong("nro_conta");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                ct++;
                System.out.println("#"+ct+" - Conta de número: "+conta+", tem o saldo de R$"+saldo+" no banco.");
            }

            System.out.println("  \nADICIONE UM REGISTO!" );

            //INSERIR DADOS
            String insert = "INSERT INTO contas VALUES (?, ?)";
            PreparedStatement stm = conn.prepareStatement(insert);

            System.out.print("Insira o número da conta: ");
            long nro = entrada.nextLong();
            stm.setLong(1,nro);

            System.out.print("Insira o valor do saldo da conta: R$");
            BigDecimal valor = entrada.nextBigDecimal();
            stm.setBigDecimal(2, valor);

            stm.executeUpdate();

            System.out.println("------------------------------------------------------------------");
            //MOSTAR BASE DE DADOS ATUAL
            System.out.println("BASE DE DADOS DEPOIS DO INSERT: ");
            ct=0;
            PreparedStatement pstm3 = conn.prepareStatement(sql1);
            ResultSet rs2 = pstm3.executeQuery();
            while (rs2.next()){
                long conta = rs2.getLong("nro_conta");
                BigDecimal saldo = rs2.getBigDecimal("saldo");
                ct++;
                System.out.println("#"+ct+" - Conta de número: "+conta+", tem o saldo de R$"+saldo+" no banco.");
            }

            System.out.println(" \nAPAGUE UM REGISTRO!");

            //DELETAR DADOS
            String delete = "DELETE FROM contas WHERE nro_conta = (?)";
            PreparedStatement stmDEL = conn.prepareStatement(delete);

            System.out.print("Insira o número da conta: ");
            nro = entrada.nextLong();
            stmDEL.setLong(1,nro);
            stmDEL.execute();

            System.out.println("------------------------------------------------------------------");
            //MOSTAR BASE DE DADOS ATUAL
            System.out.println("BASE DE DADOS DEPOIS DO DELETE:");
            ct=0;
            PreparedStatement pstmq = conn.prepareStatement(sql1);
            ResultSet rs3 = pstmq.executeQuery();
            while (rs3.next()){
                long conta = rs3.getLong("nro_conta");
                BigDecimal saldo = rs3.getBigDecimal("saldo");
                ct++;
                System.out.println("#"+ct+" - Conta de número: "+conta+", tem o saldo de R$"+saldo+" no banco.");
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
