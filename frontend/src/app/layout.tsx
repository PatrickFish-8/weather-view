import type { Metadata } from "next";
import '@mantine/core/styles.css'
import "./globals.css";
import { createTheme, MantineProvider } from "@mantine/core";
import Background from "@/components/Background"

export const metadata: Metadata = {
  title: "weather-view",
  description: "AI generated view of any location's weather in the world at that moment",
};

const theme = createTheme({
  // colors: {
  //   'ocean-blue': ['#7AD1DD', '#5FCCDB', '#44CADC', '#2AC9DE', '#1AC2D9', '#11B7CD', '#09ADC3', '#0E99AC', '#128797', '#147885'],
  //   'bright-pink': ['#F0BBDD', '#ED9BCF', '#EC7CC3', '#ED5DB8', '#F13EAF', '#F71FA7', '#FF00A1', '#E00890', '#C50E82', '#AD1374'],
  // },
});

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark">
      <body>
        <MantineProvider theme={theme}>
          <Background/>
          {children}
        </MantineProvider>
      </body>
    </html>
  );
}
