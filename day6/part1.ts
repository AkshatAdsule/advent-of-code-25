import { stdin } from "node:process";
import { text } from "node:stream/consumers";

async function readStdin() {
	return text(stdin);
}

async function main() {
	const input = await readStdin();
	const lines = input.split("\n");

	const worksheet = [];

	for (const line of lines.slice(0, lines.length - 1)) {
		const nums = line
			.split(/\s+/)
			.map((tok) => parseInt(tok, 10))
			.filter(Number.isInteger);
		worksheet.push(nums);
	}

	const operations = lines.at(-1)?.split(/\s+/) ?? [].filter((s) => s);
	let totalResults = 0;

	for (let i = 0; i < operations?.length; i++) {
		const operation = operations[i] ?? "+";
		let result = operation === "+" ? 0 : 1;

		for (const row of worksheet) {
			if (operation === "+") {
				result += row[i] ?? 0;
			} else {
				result *= row[i] ?? 1;
			}
		}

		totalResults += result;
	}

	console.log(`Part 1: ${totalResults}`);
}

await main();
