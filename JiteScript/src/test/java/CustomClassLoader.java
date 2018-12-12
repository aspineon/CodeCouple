class CustomClassLoader extends ClassLoader {
    
    public Class<?> defineClass(String name, byte[] data) {
        return super.defineClass(name, data, 0, data.length);
    }

}
