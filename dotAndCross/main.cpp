#include <iostream>

struct Vector3D
{
float x,y,z;
Vector3D() = default;
Vector3D(float a, float b, float c) 
{
    x = a;
    y = b;
    z = c;
}
// more functions here
// .....
};

float Dot(const Vector3D& A, const Vector3D& B)
{
return (A.x*B.x + A.y*B.y + A.z*B.z);
}

Vector3D Cross(const Vector3D& A, const Vector3D& B)
{
return {A.y*B.z - A.z*B.y, A.z*B.x - A.x*B.z, A.x*B.y - A.y*B.x};
}


int main() {
Vector3D a(1, 3, 6); 
Vector3D b(5, 7, 2);

// dot of a and b 
float scalar = Dot(a, b);

// cross of a and b
Vector3D c = Cross(a, b);

std::cout << "dot of a and b = " << scalar << std::endl;
std::cout << "cross of a and b = " << c.x << ", " <<  c.y <<  ", "<< c.z << std::endl;
}
