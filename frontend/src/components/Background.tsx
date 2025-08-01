import { useEffect, useRef } from "react";

const Background = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const vertexShaderSource = `
      attribute vec4 position;
      void main() {
        gl_Position = position;
      }
    `;

    const fragmentShaderSource = `precision highp float;
      uniform vec2 resolution;
      uniform float time;

      vec4 permute(vec4 x) {
        return mod(((x * 34.0) + 1.0) * x, 289.0);
      }

      vec4 fade(vec4 t) {
        return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
      }

      vec4 taylorInvSqrt(vec4 r) {
        return 1.79284291400159 - 0.85373472095314 * r;
      }

      float cnoise(vec4 P) {
        vec4 Pi0 = floor(P); // Integer part
        vec4 Pi1 = Pi0 + 1.0;
        Pi0 = mod(Pi0, 289.0);
        Pi1 = mod(Pi1, 289.0);
        vec4 Pf0 = fract(P);
        vec4 Pf1 = Pf0 - 1.0;

        vec4 ix = vec4(Pi0.x, Pi1.x, Pi0.x, Pi1.x);
        vec4 iy = vec4(Pi0.yy, Pi1.yy);
        vec4 iz0 = vec4(Pi0.zzzz);
        vec4 iz1 = vec4(Pi1.zzzz);
        vec4 iw0 = vec4(Pi0.wwww);
        vec4 iw1 = vec4(Pi1.wwww);

        vec4 ixy = permute(permute(ix) + iy);
        vec4 ixy0 = permute(ixy + iz0);
        vec4 ixy1 = permute(ixy + iz1);
        vec4 ixy00 = permute(ixy0 + iw0);
        vec4 ixy01 = permute(ixy0 + iw1);
        vec4 ixy10 = permute(ixy1 + iw0);
        vec4 ixy11 = permute(ixy1 + iw1);

        vec4 gx00 = fract(ixy00 / 7.0) - 0.5;
        vec4 gy00 = fract(floor(ixy00 / 7.0) / 7.0) - 0.5;
        vec4 gz00 = fract(floor(floor(ixy00 / 7.0) / 7.0) / 6.0) - 0.5;
        vec4 gw00 = vec4(0.75) - abs(gx00) - abs(gy00) - abs(gz00);
        vec4 sw00 = step(gw00, vec4(0.0));
        gx00 -= sw00 * (step(0.0, gx00) - 0.5);
        gy00 -= sw00 * (step(0.0, gy00) - 0.5);

        vec4 g0000 = vec4(gx00.x, gy00.x, gz00.x, gw00.x);
        vec4 g1000 = vec4(gx00.y, gy00.y, gz00.y, gw00.y);
        vec4 g0100 = vec4(gx00.z, gy00.z, gz00.z, gw00.z);
        vec4 g1100 = vec4(gx00.w, gy00.w, gz00.w, gw00.w);

        vec4 norm = taylorInvSqrt(vec4(dot(g0000, g0000), dot(g0100, g0100), dot(g1000, g1000), dot(g1100, g1100)));
        g0000 *= norm.x;
        g0100 *= norm.y;
        g1000 *= norm.z;
        g1100 *= norm.w;

        float n0000 = dot(g0000, Pf0);
        float n1000 = dot(g1000, vec4(Pf1.x, Pf0.yzw));
        float n0100 = dot(g0100, vec4(Pf0.x, Pf1.y, Pf0.zw));
        float n1100 = dot(g1100, vec4(Pf1.xy, Pf0.zw));

        vec4 fade_xyzw = fade(Pf0);
        vec2 n_x = mix(vec2(n0000, n0100), vec2(n1000, n1100), fade_xyzw.x);
        float n_xy = mix(n_x.x, n_x.y, fade_xyzw.y);
        return 2.2 * n_xy;
      }

      float sdMoon(vec2 p, float d, float ra, float rb) {
        p.y = abs(p.y);
        float a = (ra*ra - rb*rb + d*d)/(2.0*d);
        float b = sqrt(max(ra*ra - a*a,0.0));
        if (d*(p.x*b - p.y*a) > d*d*max(b - p.y, 0.0))
          return length(p - vec2(a,b));
        return max(length(p) - ra, -(length(p - vec2(d,0)) - rb));
      }

      vec2 grid(vec2 uv) {
        return floor(uv * 50.0) * 0.02;
      }

      vec3 getColor(vec2 uv, float d, float n) {
        vec3 fgColor = vec3(0.16, 0.53, 0.37);
        vec3 fgColor2 = vec3(0.16, 0.13, 0.57);
        vec3 bgColor = vec3(0.1, 0.1, 0.22);

        if (d < 0.1 && n < 0.1) {
          float mx = mod(uv.x, 0.04);
          float my = mod(uv.y, 0.04);
          return mix(fgColor, bgColor, step(0.02, min(mx, my)));
        }

        if (d < 0.1) return fgColor;

        if (n < 0.1) {
          float mx = mod(uv.x, 0.04);
          float my = mod(uv.y, 0.04);
          return mix(bgColor, fgColor2, step(0.02, min(mx, my)));
        }

        return bgColor;
      }

      void main() {
        vec2 uv = gl_FragCoord.xy / resolution.xy * 2.0 - 1.0;
        uv.y *= resolution.y / resolution.x;

        vec2 ruv = uv;
        vec2 guv = floor(ruv * 50.0) * 0.02;

        float d = 1.0;
        float ra = 0.7;
        float rb = 0.6;
        float di = 1.5;

        float noiseAmount = 0.5;
        float noiseFrequency = 3.0;
        vec2 nudgedUV = uv + vec2(cos(time * 0.2), sin(time * 0.2)) * 0.3;
        float n = cnoise(vec4(noiseFrequency * nudgedUV, time * 0.1, time * 0.05)) * noiseAmount;


        d = min(d, sdMoon(2.0 * guv + vec2(cos(-time), sin(-time)), di * sin(-time), ra, rb));
        d = smoothstep(0.0, 0.01, d);
        n = smoothstep(0.0, 0.01, n);

        vec3 color = getColor(ruv, d, n);
        gl_FragColor = vec4(color, 1.0);
      }
    `;

    const canvas = canvasRef.current!;
    const gl = canvas.getContext("webgl");

        if ( gl != null) {
            const resize = () => {
            canvas.width = window.innerWidth;
            canvas.height = window.innerHeight;
            gl.viewport(0, 0, canvas.width, canvas.height);
            };
            resize();
            window.addEventListener("resize", resize);
        
            const createShader = (type: number, source: string) => {
            const shader = gl.createShader(type)!;
            gl.shaderSource(shader, source);
            gl.compileShader(shader);
            if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
                console.error(gl.getShaderInfoLog(shader));
                gl.deleteShader(shader);
                return null;
            }
            return shader;
            };
        
            const vertexShader = createShader(gl.VERTEX_SHADER, vertexShaderSource)!;
            const fragmentShader = createShader(gl.FRAGMENT_SHADER, fragmentShaderSource)!;
        
            const program = gl.createProgram()!;
            gl.attachShader(program, vertexShader);
            gl.attachShader(program, fragmentShader);
            gl.linkProgram(program);
            gl.useProgram(program);
        
            const positionLocation = gl.getAttribLocation(program, "position");
            const buffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array([-1, -1, 1, -1, -1, 1, -1, 1, 1, -1, 1, 1]), gl.STATIC_DRAW);
            gl.enableVertexAttribArray(positionLocation);
            gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);
        
            const resolutionLocation = gl.getUniformLocation(program, "resolution");
            const timeLocation = gl.getUniformLocation(program, "time");
        
            const startTime = Date.now();
            const render = () => {
            const time = (Date.now() - startTime) * 0.001;
            gl.uniform2f(resolutionLocation, canvas.width, canvas.height);
            gl.uniform1f(timeLocation, time);
            gl.drawArrays(gl.TRIANGLES, 0, 6);
            requestAnimationFrame(render);
            };
            render();
        
            return () => window.removeEventListener("resize", resize);
        }
    }, []);

  return (
    <canvas ref={canvasRef} className="fixed top-0 left-0 w-full h-full -z-10 bg-black" />
  );
};

export default Background;
