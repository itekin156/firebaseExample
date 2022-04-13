package com.example.firebasetutorial;

public class Upload
{
    private String name;
    private String imageviewUrl;

    public Upload()
    {

    }

    public Upload(String name , String imageviewUrl)
    {
        if (name.trim().equals(""))
        {
            name = "No Name";
        }
        this.name = name;
       this.imageviewUrl = imageviewUrl;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String n)
    {
        name = n ;
    }

    public String getImageUrl()
    {
        return imageviewUrl;
    }

    public void setImageviewUrl(String imageviewUrl)
    {
        this.imageviewUrl = imageviewUrl ;
    }
}
