package bytecode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;

public class HelloXlassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            Class cl = new HelloXlassLoader().findClass("Hello");
            // 调用非静态方法的时候，需要传入已初始化的实体
            cl.getMethod("hello").invoke(cl.newInstance());
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节转换 x = 255 - x
     * 然后再用装载该字节码
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 绝对路径的值需要根据文件实际存放位置进行修改
        byte[] bytes = fileConvertToByteArray(new File("E:\\workspace\\ideaWorkSpace\\Practice\\src\\main\\resources\\Hello.xlass"));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name,bytes,0,bytes.length);
    }

    /**
     * 将指定文件转换成byte数组
     * @param file 待转换成byte数组的文件
     * @return
     */
    private byte[] fileConvertToByteArray(File file) {
        byte[] data = null;
        try{
            // 文件输入流
            FileInputStream fis = new FileInputStream(file);
            // 字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer,0,len);
            }
            data = baos.toByteArray();

            fis.close();
            baos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
