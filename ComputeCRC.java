public class ComputeCRC
{
    private static int  P_CCITT  = 0x1021;
    private static boolean crc_tabccitt_init = false;
    private static char crc_tabccitt[] = new char[256];

    public static int computeCRC(int[] num)
    {
        char crc_ccitt = 0xffff;
        int i = 0;
        for(int b: num)
        {
            crc_ccitt = update_crc_ccitt(crc_ccitt, num[i]);
            i++;
        }
        return 0x0000ffff & crc_ccitt;
    }

    private static char update_crc_ccitt( char crc, int c )
    {

        char tmp, short_c;

        short_c  = (char)(0x000000ff & c);

        if ( ! crc_tabccitt_init ) init_crcccitt_tab();

        tmp = (char)((crc >> 8) ^ short_c);
        crc = (char)((crc << 8) ^ crc_tabccitt[tmp]);

        return crc;

    }

    private static void init_crcccitt_tab( )
    {
        int i, j;
        char crc, c;

        for (i=0; i<256; i++) {

            crc = 0;
            c   = (char)( ((char) i) << 8);

            for (j=0; j<8; j++) {

                if (( (crc ^ c) & 0x8000) != 0) crc = (char)(( crc << 1 ) ^ P_CCITT);
                else                      crc = (char)( crc << 1);

                c = (char)(c << 1);
            }

            crc_tabccitt[i] = crc;
        }

        crc_tabccitt_init = true;
    }

}
