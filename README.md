# Music Visualiser Project

Name - Student Number:

Ivan Zenin - C21406436


# Description of the assignment

Music: Vampire Hearts/Cant Lie Slowed - https://www.youtube.com/watch?v=q5s0vCQ30SA&ab_channel=Enjoyer.

OOPProject Video:

## Ivan's Work
I worked on Planet.java in the project. The program displays a 3D scene containing a pulsing planet, rotating stars, and shockwaves that reacts to the audio input. The program includes several other methods for drawing the objects in the scene, such as drawPlanet(), drawStars(), drawAsteroid(), and drawShockwaves(). These methods utilize the Processing API to create 3D shapes, apply transformations, and set colors based on the audio input and elapsed time. The createStarsTexture() and createStarsSphere() methods generate the texture and shape for the starry sphere. The initializeStars() method populates the star arrays with random values, calculating initial positions for the stars.

![Planet](images/planet.png)

### How it works

### What I am most proud of in the assignment
Im most proud of the ability of all the methods working together to make such a unique visualization. The end result turned out better than I expected and it was intreseting to learn about the different ways of creating 3D shapes. My favourite method would probably be the asteroids circling around the planet as I believe they appear visually impressive. 

## Name's Work

### How it works

### What I am most proud of in the assignment

## Name's Work

### How it works

### What I am most proud of in the assignment

## Name's Work

### How it works

### What I am most proud of in the assignment

# Instructions

# Bryan put this here to help us with the readme, I will delete this after everyone completed their bits.

### Markdown Tutorial

This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

