package team.unstudio.minigameapi.datebase;

public class SQLCommandBuilder
{
    private final StringBuilder sb = new StringBuilder();
    
    public SQLCommandBuilder(){}
    
    public String build(){
        return sb.toString();
    }
    
    public SQLCommandBuilder clean(){
        sb.delete(0,sb.length()-1);
        return this;
    }
    
    public SQLCommandBuilder createDatabase(String name){
        sb.append("CREATE DATABASE ").append(name);
        return this;
    }
    
    public SQLCommandBuilder createTable(String name){
        return this;
    }
    
    public SQLCommandBuilder select(String table){
        select(table,"*");
        return this;
    }
    
    public SQLCommandBuilder select(String table,String ...colume){
        sb.append("SELECT ");
        if(colume.length==0){
            sb.append("* ");
        }else{
            for(String s:colume){
                sb.append(s).append(',');
            }
            sb.setCharAt(sb.length()-1,' ');
        }
        return this;
    }
    
    public SQLCommandBuilder from(String table){
        sb.append("FROM ").append(table).append(' ');
        return this;
    }
    
    public SQLCommandBuilder where(String where){
        sb.append("WHERE ").append(where).append(' ');
        return this;
    }
}
