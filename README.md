# BomberMan
BomberMan là một trò chơi quen thuộc trong đó nhân vật chính sẽ di chuyển trong mê cung, đặt bom để phá những chướng ngại vật cũng như tránh khỏi các vật thể lạ để tìm đường ra khỏi mê cung. 
- ![image](https://user-images.githubusercontent.com/62598736/168737340-84e2a805-4ba3-43b5-bf38-660b0415c6bf.png) Bomber là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.

-  Enemy là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Bao gồm:

-- Big Boss: Đứng im một chỗ
  ![image](https://user-images.githubusercontent.com/62598736/168737554-00ecc226-3037-48ef-bc2c-5a144f2897a4.png)
 
-- MiniBoss: Có khả năng di chuyển, có khả năng đi qua Stone
  ![image](https://user-images.githubusercontent.com/62598736/168737744-94edba30-6879-4165-97e3-c228effeab4d.png)
  
- ![image](https://user-images.githubusercontent.com/62598736/168738166-661f085c-b48f-47d3-9e7f-252776ab4006.png) Bomb là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng Flame được tạo ra.
Grass là đối tượng mà Bomber và Enemy có thể  chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó.

Wall , Box , Stone   là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
![image](https://user-images.githubusercontent.com/62598736/168738526-d7ffc1b0-c3c5-4612-a1b8-0a34e44821c7.png)![image](https://user-images.githubusercontent.com/62598736/168738585-3f31cb79-8cb5-4d65-a486-b03e64c363bb.png)![image](https://user-images.githubusercontent.com/62598736/168738638-a4d18d8b-ee52-4969-a700-8ee7e42444a4.png)

Vật phẩm
![image](https://user-images.githubusercontent.com/62598736/168738681-61e80be4-0642-4467-ab46-418c45f8333a.png)![image](https://user-images.githubusercontent.com/62598736/168738704-c05afe98-f537-4496-bddf-7208e536e3e9.png)![image](https://user-images.githubusercontent.com/62598736/168738744-e593e79d-132a-4a50-847d-f1546c29978c.png)
