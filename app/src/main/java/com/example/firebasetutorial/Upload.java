package com.example.firebasetutorial;

public class Upload
{
    private String name;
    private String imageViewUrl;

    public Upload()
    {

    }

    public Upload(String n , String imgViewUrl)
    {
        if (n.trim().equals(""))
        {
            n = "No Name";
        }
        name = n;
       imageViewUrl = imgViewUrl;
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
        return imageViewUrl;
    }

    public void setImageUrl(String imgViewUrl)
    {
        imageViewUrl = imgViewUrl ;
    }
}
