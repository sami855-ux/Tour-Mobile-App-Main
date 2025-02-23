![image](https://github.com/user-attachments/assets/041609c5-220e-4241-94dc-9621cfeda0a0)   ![image](https://github.com/user-attachments/assets/57ff58b7-691a-47ff-8cf7-593a6f189db9)
```html  
<!DOCTYPE html>  
<html lang="en">  
<head>  
    <meta charset="UTF-8">  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <title>Flexbox Image Layout</title>  
    <style>  
         
        .flex-container {  
            display: flex;  
            justify-content: center; /* Center horizontally */  
            align-items: center; /* Center vertically */  
            height: 30vh; /* Full height of the viewport */  
            background-color: #f0f0f0; /* Light gray background */
            gap: 5px;
        }  

        .flex-item {  
            max-width: 45%; /* Responsive image size */  
            height: 100%; /* Maintain aspect ratio */  
        }  
    </style>  
</head>  
<body>  

<div class="flex-container">  
    <img src="https://github.com/user-attachments/assets/041609c5-220e-4241-94dc-9621cfeda0a0" alt="Descriptive Alt Text" class="flex-item">
    <img src="https://github.com/user-attachments/assets/57ff58b7-691a-47ff-8cf7-593a6f189db9" alt="Descriptive Alt Text" class="flex-item">  
</div>  

</body>  
</html>
```
