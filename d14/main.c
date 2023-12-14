#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char grid[100][100];
char memo[100][100];

void tilt(int dir);

int main (int argc, char * argv[])
{
    char *filename = "input";
    long result=0;
    int part=1;
    char buf[110];
    FILE * fp;
    int x=0,y,offset, start, cycle;
    if(argc > 1)
        part=atoi(argv[1]);
    if(argc > 2)
        filename=argv[2];
    if((fp = fopen (filename, "r"))==NULL)
    {
        printf("error opening file %s\n", filename);
        return 1;
    }
    memset(grid,'#',sizeof(grid));
    while(fgets(buf, 110, fp) != NULL)
    {
        strncpy(grid[x++],buf,strlen(buf)-1);
    }
    offset=100-x;
    if(part==1)
        tilt(0);
    else
        for(int i=0;i<1000000000;i++)
        {
            if(!(i%100))
            {
                memcpy(memo,grid,sizeof(grid));
                start=i;
            }
            tilt(0);
            tilt(1);
            tilt(2);
            tilt(3);
            if(!memcmp(memo,grid,sizeof(grid)))
            {
                cycle=i-start+1;
                while(i<=1000000000)
                    i+=cycle;
                i-=cycle;
            }
        }
    for(x=0;x<100;x++)
        for(y=0;y<100;y++)
            if(grid[x][y]=='O')
                result+=(100-x)-offset;
    printf("%ld\n",result);
    return 0;
}

void tilt(int dir)
{
    static int x,y;
    if(!dir)
        for(x=0;x<99;x++)
            for(y=0;y<100;y++)
            {
                if(grid[x][y]!='.')
                    continue;
                for(int i=x+1;i<100;i++)
                    if(grid[i][y]=='.')
                        continue;
                    else if(grid[i][y]=='O')
                    {
                        grid[i][y]='.';
                        grid[x][y]='O';
                        break;
                    }
                    else
                        break;
            }
    else if(dir==1)
        for(y=0;y<99;y++)
            for(x=0;x<100;x++)
            {
                if(grid[x][y]!='.')
                    continue;
                for(int i=y+1;i<100;i++)
                    if(grid[x][i]=='.')
                        continue;
                    else if(grid[x][i]=='O')
                    {
                        grid[x][i]='.';
                        grid[x][y]='O';
                        break;
                    }
                    else
                        break;
            }
    else if(dir==2)
        for(x=99;x>0;x--)
            for(y=0;y<100;y++)
            {
                if(grid[x][y]!='.')
                    continue;
                for(int i=x-1;x>=0;i--)
                    if(grid[i][y]=='.')
                        continue;
                    else if(grid[i][y]=='O')
                    {
                        grid[i][y]='.';
                        grid[x][y]='O';
                        break;
                    }
                    else
                        break;
            }
    else if(dir==3)
        for(y=99;y>0;y--)
            for(x=0;x<100;x++)
            {
                if(grid[x][y]!='.')
                    continue;
                for(int i=y-1;i>=0;i--)
                    if(grid[x][i]=='.')
                        continue;
                    else if(grid[x][i]=='O')
                    {
                        grid[x][i]='.';
                        grid[x][y]='O';
                        break;
                    }
                    else
                        break;
            }
    else
        printf("ERROR\n");
}
